import React, { useState, useEffect } from 'react';
import { useLocation, Link } from 'react-router-dom';
import './Navbar.css';
import logo from '../../Asserts/Logo.webp'; // Corrected path

function Navbar() {
  const location = useLocation();
  const [pageTitle, setPageTitle] = useState('');

  useEffect(() => {
    setPageTitle(location.pathname);
  }, [location.pathname]);

  // Function to determine if the current path matches the route
  const isActive = (path) => location.pathname === path ? 'active' : '';

  return (
    <>
    <div className='outer-div'>
      <div className='nav-heading'>
        <div className='page-title'>
          <p>Abricate Engineering</p> 
        </div>
        <div className='logo-div'>
          <img src={logo} className='logo' alt='Abricate Engineering Logo' />
        </div>
      </div>
      <div className='navigation-buttons-div'>
        <ul>
          <li>
            <Link to='/material-report' className={isActive('/material-report')}>Material Report</Link>
          </li>
          <li>
            <Link to='/receipe-consumption' className={isActive('/receipe-consumption')}>Recipe Consumption</Link>
          </li>
        </ul>
      </div>
    </div>
    <div className='background-div'></div>
    <div className='backgroundSticky-div'></div>
    </>
  );
}

export default Navbar;
