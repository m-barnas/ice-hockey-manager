import React from 'react';

import classes from './Logo.css';

import iceHockeyManagerLogo from '../../assets/images/ice-hockey-manager-logo.png';

const logo = (props) => (
    <div className={classes.Logo}>
        <img src={iceHockeyManagerLogo} alt="Ice Hockey Manager LOGO" />
        <a href="/">Ice Hockey Manager</a>
    </div>
);

export default logo;