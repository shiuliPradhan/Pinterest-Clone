import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import ProtectedRoute from "../components/layout/ProtectedRoute";

// Auth Pages
import SignInPage from "../pages/Auth/SignInPage";
import SignUpPage from "../pages/Auth/SignUpPage";
import ForgotPasswordPage from "../pages/Auth/ForgotPasswordPage";
import ResetPasswordPage from "../pages/Auth/ResetPasswordPage";

// Home / Feed
import FeedPage from "../pages/Home/FeedPage";

// Search / Explore
import ExplorePage from "../pages/Explore/ExplorePage";
import SearchResultsPage from "../pages/Explore/SearchResultsPage";

// Profile
import ProfilePage from "../pages/Profile/ProfilePage";
import FollowersPage from "../pages/Profile/FollowersPage";
import FollowingPage from "../pages/Profile/FollowingPage";

// Pins
import PinPage from "../pages/Pin/PinPage";
import CreatePinPage from "../pages/Pin/CreatePinPage";
import EditPinPage from "../pages/Pin/EditPinPage";

// Boards
import BoardPage from "../pages/Boards/BoardPage";
import CollaboratorsPage from "../pages/Boards/CollaboratorsPage";

// Business
import BusinessDirectoryPage from "../pages/Business/BusinessDirectoryPage";

// Notifications
import NotificationPage from "../pages/Notifications/NotificationPage";

// Sponsored
import SponsoredPinsPage from "../pages/Misc/SponsoredPinsPage";

// Misc
import NotFound from "../pages/Misc/NotFound";

const AppRoutes: React.FC = () => {
  return (
    <Routes>

      {/* ---------------- PUBLIC ROUTES ---------------- */}
      <Route path="/auth/signin" element={<SignInPage />} />
      <Route path="/auth/signup" element={<SignUpPage />} />
      <Route path="/auth/forgot-password" element={<ForgotPasswordPage />} />
      <Route path="/auth/reset-password/:token" element={<ResetPasswordPage />} />

      {/* Homepage Redirect */}
      <Route path="/" element={<Navigate to="/feed" replace />} />


      {/* ---------------- PROTECTED ROUTES ---------------- */}
      <Route
        path="/feed"
        element={
          <ProtectedRoute>
            <FeedPage />
          </ProtectedRoute>
        }
      />

      <Route
        path="/explore"
        element={
          <ProtectedRoute>
            <ExplorePage />
          </ProtectedRoute>
        }
      />

      <Route
        path="/search"
        element={
          <ProtectedRoute>
            <SearchResultsPage />
          </ProtectedRoute>
        }
      />

      <Route
        path="/profile"
        element={
          <ProtectedRoute>
            <ProfilePage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/profile/followers"
        element={
          <ProtectedRoute>
            <FollowersPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/profile/following"
        element={
          <ProtectedRoute>
            <FollowingPage />
          </ProtectedRoute>
        }
      />

      {/* Pins */}
      <Route
        path="/pin/:id"
        element={
          <ProtectedRoute>
            <PinPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/pin/create"
        element={
          <ProtectedRoute>
            <CreatePinPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/pin/:id/edit"
        element={
          <ProtectedRoute>
            <EditPinPage />
          </ProtectedRoute>
        }
      />

      {/* Boards */}
      <Route
        path="/boards/:id"
        element={
          <ProtectedRoute>
            <BoardPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/boards/:id/collaborators"
        element={
          <ProtectedRoute>
            <CollaboratorsPage />
          </ProtectedRoute>
        }
      />

      {/* Business */}
      <Route
        path="/business"
        element={
          <ProtectedRoute>
            <BusinessDirectoryPage />
          </ProtectedRoute>
        }
      />

      {/* Notifications */}
      <Route
        path="/notifications"
        element={
          <ProtectedRoute>
            <NotificationPage />
          </ProtectedRoute>
        }
      />

      {/* Sponsored Pins */}
      <Route
        path="/sponsored"
        element={
          <ProtectedRoute>
            <SponsoredPinsPage />
          </ProtectedRoute>
        }
      />

      {/* 404 */}
      <Route path="*" element={<NotFound />} />

    </Routes>
  );
};

export default AppRoutes;
