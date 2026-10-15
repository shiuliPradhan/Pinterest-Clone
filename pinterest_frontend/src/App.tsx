import React from "react";
import { BrowserRouter, useLocation } from "react-router-dom";
import AppRoutes from "./routes/AppRoutes";
import NavBar from "./components/layout/NavBar";
import Footer from "./components/layout/Footer";
import { AuthProvider } from "./context/AuthContext";
import { UIProvider } from "./context/UIContext";
import { NotificationProvider } from "./context/NotificationContext";

const LayoutWrapper: React.FC = () => {
  const location = useLocation();

  // Hide navbar/footer on auth routes
  const hideLayout = location.pathname.startsWith("/auth");

  return (
    <div className={`flex flex-col min-h-screen ${hideLayout ? "bg-white" : "bg-gray-50"}`}>
      {!hideLayout && <NavBar />}

      <main className="flex-1">
        <AppRoutes />
      </main>

      {/* {!hideLayout && <Footer />} */}
    </div>
  );
};

const App: React.FC = () => {
  return (
    <AuthProvider>
      <UIProvider>
        <NotificationProvider>
          <BrowserRouter>
            <LayoutWrapper />
          </BrowserRouter>
        </NotificationProvider>
      </UIProvider>
    </AuthProvider>
  );
};

export default App;
