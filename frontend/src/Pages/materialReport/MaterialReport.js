import axios from 'axios';
import { useState } from 'react';
import './MaterialReport.css';
import { IoIosPrint } from 'react-icons/io';
import { IoMdCloudDownload } from 'react-icons/io';

function MaterialReport() {
  const [materialReports, setMaterialReports] = useState([
    { achWeight: 0, materialName: 'null', setWeight: 0 }
  ]);

  const [error, setError] = useState(null);
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const handleStartDateChange = (e) => {
    setStartDate(e.target.value);
  };

  const handleEndDateChange = (e) => {
    setEndDate(e.target.value);
  };

  const handleFetch = () => {
    if (startDate && endDate) {
      axios
        .get(
          `http://localhost:8080/api/data/material-report?startDate=${startDate}&endDate=${endDate}`
        )
        .then((response) => {
          setMaterialReports(response.data);
          console.log(response.data);
        })
        .catch((error) => {
          setError(error);
        });
    }
  };

  const handlePrint = () => {
    window.print();
  };

  return (
    <div className="outerDiv">
      <div className="display-container">
        <h1 >Material Report</h1>
        <div className="date-selectionDiv">
          <label>
            Start Date : 
            <input
              type="date"
              value={startDate}
              onChange={handleStartDateChange}
            />
          </label>
          <label>
            End Date : 
            <input
              type="date"
              value={endDate}
              onChange={handleEndDateChange}
            />
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

        {error && <div>Error: {error.message}</div>}
        {!error && (
          <table border="1" cellPadding="10" cellSpacing="1">
            <thead className="table-title">
              <tr>
                <th>Material Name</th>
                <th>Set Weight</th>
                <th>Actual Weight</th>
                <th>Error</th>
              </tr>
            </thead>
            <tbody>
              {materialReports.map((material, idx) => (
                <tr key={idx}>
                  <td>{material.materialName.trim()}</td>
                  <td>{material.setWeight}</td>
                  <td>{material.achWeight}</td>
                  <td>{material.achWeight - material.setWeight}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default MaterialReport;
