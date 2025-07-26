import React from "react";
import { Route, Routes } from "react-router-dom";
import { useAuth0 } from "@auth0/auth0-react";
import { LandingPage } from "./pages/landing-page";
import { DashboardPage } from "./pages/dashboard-page";

function App() {
  const { isLoading } = useAuth0();

  // This prevents the app from rendering before Auth0 is ready
  if (isLoading) {
    return (
      <div className="flex h-screen items-center justify-center">
        <div>Loading...</div>
      </div>
    );
  }

  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/dashboard" element={<DashboardPage />} />
    </Routes>
  );
}

export default App;