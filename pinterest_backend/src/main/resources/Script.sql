-- Create database
CREATE DATABASE IF NOT EXISTS pinterest_db;
USE pinterest_db;

-- 1. USERS (auth)
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(100) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. USER PROFILE (1-1)
CREATE TABLE user_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    display_name VARCHAR(150),
    bio VARCHAR(500),
    profile_image_url VARCHAR(400),
    phone_number VARCHAR(20),
    website_url VARCHAR(200),
    location VARCHAR(100),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY ux_user_profile_user(user_id),
    CONSTRAINT fk_user_profile_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. LOGIN ATTEMPTS (for circuit breaker / lockout logic) — US-02
CREATE TABLE login_attempts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    attempt_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    success BOOLEAN,
    ip_address VARCHAR(50),
    CONSTRAINT fk_login_attempts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE INDEX idx_login_attempts_user_time ON login_attempts(user_id, attempt_timestamp);

-- 4. PASSWORD RESET TOKENS (US-02)
CREATE TABLE password_reset_tokens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL,
    mobile_verified BOOLEAN DEFAULT FALSE,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prt_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE INDEX idx_prt_token ON password_reset_tokens(token);

-- 5. BOARDS
CREATE TABLE boards (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    is_private BOOLEAN DEFAULT FALSE,
    cover_image_url VARCHAR(400),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_board_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE INDEX idx_board_title ON boards(title);

-- 6. BOARD COLLABORATORS (US-07: collaborators after accepting invite)
CREATE TABLE board_collaborators (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    board_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role VARCHAR(50) DEFAULT 'CONTRIBUTOR', -- owner/collaborator
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bc_board FOREIGN KEY (board_id) REFERENCES boards(id) ON DELETE CASCADE,
    CONSTRAINT fk_bc_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY ux_board_user(board_id, user_id)
);

-- 7. PINS
CREATE TABLE pins (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    board_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    source_url VARCHAR(400),
    status ENUM('DRAFT','PUBLISHED') DEFAULT 'PUBLISHED',
    is_private BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_pin_board FOREIGN KEY (board_id) REFERENCES boards(id) ON DELETE CASCADE,
    CONSTRAINT fk_pin_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE INDEX idx_pin_title ON pins(title);

-- 8. PIN MEDIA (multiple images/videos per pin) — US-03
CREATE TABLE pin_media (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pin_id BIGINT NOT NULL,
    media_url VARCHAR(500) NOT NULL,
    media_type VARCHAR(20) NOT NULL, -- IMAGE, VIDEO
    ord INT DEFAULT 0,
    CONSTRAINT fk_pm_pin FOREIGN KEY (pin_id) REFERENCES pins(id) ON DELETE CASCADE
);

-- 9. TAGS / KEYWORDS (for search suggestions) — US-03 / US-05
CREATE TABLE pin_tags (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tag VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE pin_tag_map (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pin_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    CONSTRAINT fk_ptm_pin FOREIGN KEY (pin_id) REFERENCES pins(id) ON DELETE CASCADE,
    CONSTRAINT fk_ptm_tag FOREIGN KEY (tag_id) REFERENCES pin_tags(id) ON DELETE CASCADE,
    UNIQUE KEY ux_pin_tag(pin_id, tag_id)
);

-- 10. PIN SAVES (user saved/bookmarked pins -> a pin can be saved to boards, track per-board saves)
CREATE TABLE pin_saves (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pin_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    board_id BIGINT, -- which board the user saved it to (nullable)
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ps_pin FOREIGN KEY (pin_id) REFERENCES pins(id) ON DELETE CASCADE,
    CONSTRAINT fk_ps_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_ps_board FOREIGN KEY (board_id) REFERENCES boards(id) ON DELETE CASCADE,
    UNIQUE KEY ux_pin_user(pin_id, user_id, coalesce(board_id,0))
);

-- 11. PIN LIKES / ENGAGEMENT (for regular pins and sponsored pins interactions)
CREATE TABLE pin_likes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pin_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pl_pin FOREIGN KEY (pin_id) REFERENCES pins(id) ON DELETE CASCADE,
    CONSTRAINT fk_pl_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY ux_pin_like(pin_id, user_id)
);

-- 12. FOLLOWERS (US-06)
CREATE TABLE followers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_follow_follower FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_follow_following FOREIGN KEY (following_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY ux_follow(follower_id, following_id)
);

-- 13. INVITATIONS (US-07)
CREATE TABLE invitations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    board_id BIGINT,              -- null for connection invite, board_id for board collaboration
    invitation_type VARCHAR(50) DEFAULT 'BOARD_COLLAB', -- BOARD_COLLAB or CONNECTION
    message VARCHAR(500),
    status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, ACCEPTED, DECLINED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_inv_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_inv_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_inv_board FOREIGN KEY (board_id) REFERENCES boards(id) ON DELETE CASCADE
);
CREATE INDEX idx_inv_receiver ON invitations(receiver_id);

-- 14. BUSINESS PROFILE (US-08)
CREATE TABLE business_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    business_name VARCHAR(255) NOT NULL,
    logo_url VARCHAR(400),
    description VARCHAR(1000),
    website_url VARCHAR(300),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_business_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 15. SHOWCASE (US-08)
CREATE TABLE showcase (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    cover_image_url VARCHAR(400),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_showcase_business FOREIGN KEY (business_id) REFERENCES business_profile(id) ON DELETE CASCADE
);

-- 16. CAMPAIGN (US-09)
CREATE TABLE campaign (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    business_id BIGINT NOT NULL,
    campaign_name VARCHAR(255) NOT NULL,
    campaign_description VARCHAR(1000),
    budget DECIMAL(14,2),
    start_date DATE,
    end_date DATE,
    status VARCHAR(50) DEFAULT 'ACTIVE', -- ACTIVE, PAUSED, ENDED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_campaign_business FOREIGN KEY (business_id) REFERENCES business_profile(id) ON DELETE CASCADE
);

-- 17. SPONSORED_PIN (US-09)
CREATE TABLE sponsored_pin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pin_id BIGINT NOT NULL,
    business_id BIGINT NOT NULL,
    campaign_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    click_rate DECIMAL(9,4) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_sp_pin_pin FOREIGN KEY (pin_id) REFERENCES pins(id) ON DELETE CASCADE,
    CONSTRAINT fk_sp_pin_business FOREIGN KEY (business_id) REFERENCES business_profile(id) ON DELETE CASCADE,
    CONSTRAINT fk_sp_pin_campaign FOREIGN KEY (campaign_id) REFERENCES campaign(id) ON DELETE CASCADE,
    UNIQUE KEY ux_sp_pin_campaign(pin_id, campaign_id)
);
CREATE INDEX idx_sp_active ON sponsored_pin(is_active);

-- 18. NOTIFICATIONS (used for invites / campaign alerts / system messages)
CREATE TABLE notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    type VARCHAR(100),
    payload JSON,
    read_flag BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notif_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE INDEX idx_notif_user ON notifications(user_id);

-- 19. OPTIONAL: SESSION / JWT BLACKLIST (for logout / invalidation)
CREATE TABLE sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    session_token VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    CONSTRAINT fk_session_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Indexes to help search (US-05)
CREATE INDEX idx_pin_desc ON pins(description(255));
CREATE INDEX idx_tag_tag ON pin_tags(tag);

-- End of DDL
INSERT INTO users (email, username, password, roles) VALUES
('arun@example.com', 'arun', '$2a$12$hZGOVSAsTGlxD9N4E.k4e.xf.2zMnVP7vsfIjRFdkUawekGTZGx2O', 'USER'),
('meera@example.com', 'meera', '$2a$12$hZGOVSAsTGlxD9N4E.k4e.xf.2zMnVP7vsfIjRFdkUawekGTZGx2O', 'USER'),
('vijay@example.com', 'vijay', '$2a$12$hZGOVSAsTGlxD9N4E.k4e.xf.2zMnVP7vsfIjRFdkUawekGTZGx2O', 'USER'),
('riya@example.com', 'riya', '$2a$12$hZGOVSAsTGlxD9N4E.k4e.xf.2zMnVP7vsfIjRFdkUawekGTZGx2O', 'BUSINESS'),
('ram@example.com', 'ram', '$2a$12$hZGOVSAsTGlxD9N4E.k4e.xf.2zMnVP7vsfIjRFdkUawekGTZGx2O', 'BUSINESS');

INSERT INTO user_profile (user_id, display_name, bio, profile_image_url, phone_number, website_url, location)
VALUES
(1, 'Arun Kumar', 'Traveler & Photographer', 'https://picsum.photos/200?1', '9876543210', 'https://arun.example.com', 'Bangalore'),
(2, 'Meera Devi', 'Fashion Designer', 'https://picsum.photos/200?2', '9876543211', 'https://meera.example.com', 'Chennai'),
(3, 'Vijay Raj', 'Food Blogger', 'https://picsum.photos/200?3', '9876543212', 'https://vijay.example.com', 'Hyderabad'),
(4, 'Riya Sharma', 'Marketing Expert', 'https://picsum.photos/200?4', '9876543213', 'https://riya.example.com', 'Delhi'),
(5, 'Ram Verma', 'Business Owner', 'https://picsum.photos/200?5', '9876543214', 'https://ram.example.com', 'Mumbai');

INSERT INTO boards (user_id, title, description, is_private, cover_image_url)
VALUES
(1, 'Travel Spots', 'Best places I visited', 0, 'https://picsum.photos/300?travel'),
(2, 'Fashion Ideas', 'My style inspirations', 0, 'https://picsum.photos/300?fashion'),
(3, 'Food Recipes', 'Collection of recipes', 0, 'https://picsum.photos/300?food'),
(4, 'Marketing Tips', 'Business marketing content', 1, 'https://picsum.photos/300?business'),
(5, 'Tech Gadgets', 'Latest tech products', 0, 'https://picsum.photos/300?tech');

INSERT INTO pins (board_id, user_id, title, description, source_url)
VALUES
(1, 1, 'Eiffel Tower Night View', 'Paris travel photo', 'https://example.com/eiffel'),
(1, 1, 'Himachal Trek', 'Mountains & clouds', 'https://example.com/himalaya'),
(2, 2, 'Summer Outfit', 'Street style look', 'https://example.com/outfit'),
(3, 3, 'Pasta Alfredo', 'Creamy white sauce pasta', 'https://example.com/pasta'),
(5, 5, 'MacBook Pro Setup', 'Minimal desk setup', 'https://example.com/macbook');

INSERT INTO pin_media (pin_id, media_url, media_type, ord)
VALUES
(1, 'https://picsum.photos/500?eiffel', 'IMAGE', 1),
(2, 'https://picsum.photos/500?mountains', 'IMAGE', 1),
(3, 'https://picsum.photos/500?fashion', 'IMAGE', 1),
(4, 'https://picsum.photos/500?food', 'IMAGE', 1),
(5, 'https://picsum.photos/500?techdesk', 'IMAGE', 1);


INSERT INTO pin_tags (tag) VALUES
('travel'), ('mountains'), ('fashion'), ('food'), ('technology');

INSERT INTO pin_tag_map (pin_id, tag_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);


INSERT INTO pin_saves (pin_id, user_id, board_id) VALUES
(1, 2, 1),
(2, 1, 1),
(3, 1, 2),
(4, 4, 3),
(5, 3, 5);

INSERT INTO pin_likes (pin_id, user_id) VALUES
(1, 3),
(1, 4),
(2, 5),
(3, 1),
(4, 2);

INSERT INTO followers (follower_id, following_id) VALUES
(1, 2),
(2, 1),
(3, 1),
(4, 1),
(5, 3);


INSERT INTO invitations (sender_id, receiver_id, board_id, invitation_type, message, status)
VALUES
(1, 2, 1, 'BOARD_COLLAB', 'Join my travel board!', 'PENDING'),
(3, 1, NULL, 'CONNECTION', 'Let\'s connect!', 'ACCEPTED'),
(4, 5, 4, 'BOARD_COLLAB', 'Work with us!', 'PENDING'),
(2, 3, NULL, 'CONNECTION', 'Follow me!', 'DECLINED'),
(5, 1, 5, 'BOARD_COLLAB', 'Tech content collab?', 'PENDING');


INSERT INTO business_profile (user_id, business_name, logo_url, description, website_url)
VALUES
(4, 'Riya Marketing', 'https://picsum.photos/200?logo1', 'Digital marketing expert', 'https://riyamarketing.com'),
(5, 'Ram Electronics', 'https://picsum.photos/200?logo2', 'Online electronics store', 'https://ramelectro.com'),
(4, 'Riya Ads', 'https://picsum.photos/200?logo3', 'Social media ad agency', 'https://riyaads.com'),
(5, 'TechZone', 'https://picsum.photos/200?logo4', 'Latest gadget reviews', 'https://techzone.com'),
(4, 'BrandBoost', 'https://picsum.photos/200?logo5', 'Brand promotional company', 'https://brandboost.com');

INSERT INTO showcase (business_id, title, description, cover_image_url)
VALUES
(1, 'Top Clients', 'Our successful clients list', 'https://picsum.photos/400?clients'),
(2, 'Featured Gadgets', 'Latest tech gadgets collection', 'https://picsum.photos/400?gadgets'),
(3, 'Ad Campaigns', 'Our best campaigns', 'https://picsum.photos/400?ads'),
(4, 'Tech Reviews', 'Expert reviews on tech', 'https://picsum.photos/400?reviews'),
(5, 'Brand Strategy', 'Business branding tips', 'https://picsum.photos/400?strategy');


INSERT INTO campaign (business_id, campaign_name, campaign_description, budget, start_date, end_date, status)
VALUES
(1, 'Summer Sale', 'Discounts on marketing services', 20000, '2025-05-01', '2025-06-01', 'ACTIVE'),
(2, 'Diwali Tech Blast', 'Offers on gadgets', 50000, '2025-09-01', '2025-11-01', 'ACTIVE'),
(3, 'Brand Awareness', 'Promotional ads', 30000, '2025-03-01', '2025-04-01', 'PAUSED'),
(4, 'Review Week', 'Tech review promotions', 15000, '2025-01-10', '2025-01-20', 'ENDED'),
(5, 'Boost Your Brand', 'Special brand campaign', 25000, '2025-02-01', '2025-03-01', 'ACTIVE');


INSERT INTO sponsored_pin (pin_id, business_id, campaign_id, is_active, click_rate)
VALUES
(1, 1, 1, 1, 0.40),
(2, 2, 2, 1, 0.55),
(3, 3, 3, 0, 0.10),
(4, 4, 4, 0, 0.20),
(5, 5, 5, 1, 0.35);

INSERT INTO notifications (user_id, type, payload)
VALUES
(1, 'FOLLOW', JSON_OBJECT('from', 2)),
(2, 'LIKE', JSON_OBJECT('pin', 1)),
(3, 'INVITE', JSON_OBJECT('board', 1)),
(4, 'CAMPAIGN_UPDATE', JSON_OBJECT('campaign', 1)),
(5, 'SYSTEM', JSON_OBJECT('message', 'Welcome to Pinterest Clone!'));

