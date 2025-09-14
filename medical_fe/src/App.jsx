import React from "react";
import { Routes, Route } from "react-router-dom";
import PrivateRoute from "./components/PrivateRoute";

// pages
import Home from "./pages/user/Home";
import MedicalDashboard from "./pages/admin/MedicalDashboard";
import Login from "./pages/user/Login";
import Appointment from "./pages/user/Appointment";
import Reception from "./pages/staff/Reception";
import DoctorConsultation from "./pages/doctor/Consultation";

function App() {
  return (
    <Routes>
      {/* Public route */}
       <Route path="/" element={
        // <Home />
        <MedicalDashboard/>
       } />
      <Route path="/login" element={
        <Login />
      } />

      {/* User routes */}
      <Route
        path="/appointment"
        element={
          <PrivateRoute allowedRoles={["user"]}>
            <Appointment />
          </PrivateRoute>
        }
      />

      {/* Staff routes */}
      <Route
        path="/reception"
        element={
          <PrivateRoute allowedRoles={["staff"]}>
            <Reception />
          </PrivateRoute>
        }
      />

      {/* Doctor routes */}
      <Route
        path="/consultation"
        element={
          <PrivateRoute allowedRoles={["doctor"]}>
            <DoctorConsultation />
          </PrivateRoute>
        }
      />

      {/* Admin routes */}
      <Route
        path="/admin"
        element={
          <PrivateRoute allowedRoles={["admin"]}>
            {/* <AdminDashboard /> */}
          </PrivateRoute>
        }
      />

      {/* Không có quyền */}
      <Route path="/403" element={<h1>403 - Không có quyền truy cập</h1>} />

      {/* 404 */}
      <Route path="*" element={<h1>404 - Not Found</h1>} />
    </Routes>
  );
}

export default App;
