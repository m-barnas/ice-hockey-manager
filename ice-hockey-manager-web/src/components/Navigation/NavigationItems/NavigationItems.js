import React from 'react';

import classes from './NavigationItems.css';

import NavigationItem from './NavigationItem/NavigationItem';

const navigationItems = (props) => (
    <ul className={classes.NavigationItems}>
        <NavigationItem link="/" active>HOME</NavigationItem>
        <NavigationItem link="/">GAMES</NavigationItem>
        <NavigationItem link="/">PLAYERS</NavigationItem>
        <NavigationItem link="/">TEAMS</NavigationItem>
        <NavigationItem link="/">MANAGERS</NavigationItem>
    </ul>
);

export default navigationItems;