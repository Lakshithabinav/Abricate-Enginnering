import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { IoIosPrint } from 'react-icons/io';
import { IoMdCloudDownload } from 'react-icons/io';
import './RecipeConsumption.css';

function RecipeConsumption() {
  const [recipeConsumptions, setRecipeConsumptions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  let i =0;
  // Function to load dates from session storage
  const loadDatesFromSession = () => {
    const storedStartDate = sessionStorage.getItem('startDate');
    const storedEndDate = sessionStorage.getItem('endDate');
    if (storedStartDate) setStartDate(storedStartDate);
    if (storedEndDate) setEndDate(storedEndDate);
    
  };

  useEffect(() => {
    loadDatesFromSession();
  }, []);



  const handleStartDateChange = (e) => {
    const newStartDate = e.target.value;
    setStartDate(newStartDate);
    sessionStorage.setItem('startDate', newStartDate);
  }

  const handleEndDateChange = (e) => {
    const newEndDate = e.target.value;
    setEndDate(newEndDate);
    sessionStorage.setItem('endDate', newEndDate);
  }

  const handleFetch = () => {
    if (startDate && endDate) {
      setLoading(true);
      axios.get(`http://localhost:8080/api/data/recipe-consumptions?startDate=${startDate}&endDate=${endDate}`)
        .then(response => {
          setRecipeConsumptions(response.data);
          console.log(response.data);
         
        })
        .catch(error => {
          setError(error);
          
        });
    }
  };

  const handlePrint = () => {
    window.print();
  };

  
  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div className="outerDiv">
      <div className="display-container">
        <h1>Recipe Consumptions</h1>
        <div className="date-selectionDiv">
          <label>
            Start Date:
            <input type="date" value={startDate} onChange={handleStartDateChange} />
          </label>
          <label>
            End Date:
            <input type="date" value={endDate} onChange={handleEndDateChange} />
          </label>
          <button className="fetchbutton" onClick={handleFetch}>
            <IoMdCloudDownload />
            Fetch
          </button>
          <button className="fetchbutton" onClick={handlePrint}>
            <IoIosPrint />
            Print
          </button>
        </div>
        
        <table border="1" cellPadding="10" cellSpacing="2">
          {recipeConsumptions.map((consumption, index) => {
            const totalSetWeight = consumption.materialReport.reduce((total, material) => total + material.setWeight, 0);
            const totalAchWeight = consumption.materialReport.reduce((total, material) => total + material.achWeight, 0);

            return (
              <React.Fragment key={index}>
                
                  <tr>
                    <td colSpan="4" style={{ fontWeight: 'bold', textAlign: 'center' }}>
                      Recipe name: {consumption.formulaName}
                    </td>
                  </tr>
                  <tr>
                    <th>Material Name</th>
                    <th>Set Weight</th>
                    <th>Actual Weight</th>
                    <th>Error</th>
                  </tr>
                
                <tbody>
                  {consumption.materialReport.map((material, idx) => (
                    <tr key={idx}>
                      <td>{material.materialName}</td>
                      <td>{material.setWeight}</td>
                      <td>{material.achWeight}</td>
                      <td>{material.achWeight - material.setWeight}</td>
                    </tr>
                  ))}
                  <tr>
                    <td><strong>Total</strong></td>
                    <td><strong>{totalSetWeight}</strong></td>
                    <td><strong>{totalAchWeight}</strong></td>
                    <td><strong>{totalAchWeight - totalSetWeight}</strong></td>
                  </tr>
                </tbody>
              </React.Fragment>
            );
          })}
        </table>
      </div>
    </div>
  );
}

export default RecipeConsumption;
