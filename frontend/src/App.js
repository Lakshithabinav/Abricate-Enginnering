import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [recipeConsompsions, setRecipeConsompsions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get('http://localhost:8080/api/data/recipe-consumptions')
      .then(response => {
        setRecipeConsompsions(response.data);
        setLoading(false);
      })
      .catch(error => {
        setError(error);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div>
      <h1>Recipe Consumptions</h1>
      <ul>
        {recipeConsompsions.map((consompsion, index) => (
          <li key={index}>
            <h2>{consompsion.formulaName}</h2>
            <ul>
              {consompsion.materialReport.map((material, idx) => (
                <li key={idx}>
                  <strong>{material.materialName}</strong> - Achieved Weight: {material.achWeight}, Set Weight: {material.setWeight}
                </li>
              ))}
            </ul>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
