import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import RecipeConsumption from './Pages/receipeConsumption/RecipeConsumption';
import MaterialReport from './Pages/materialReport/MaterialReport';
import Navbar from './Components/navBar/Navbar';

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/material-report" element={<MaterialReport />} />
        <Route path="/receipe-consumption" element={<RecipeConsumption />} />
      </Routes>
    </Router>
  );
}

export default App;
