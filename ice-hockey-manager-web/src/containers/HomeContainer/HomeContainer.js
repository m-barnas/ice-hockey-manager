import React, { Component } from 'react';

// css
import './HomeContainer.css';

class HomeContainer extends Component {
    render() {
        return (
            <div>
                <h1>Ice Hockey Manager</h1>
                <h3>Manage your favorite hockey team!</h3>
                <img
                    src={'../../assets/pics/hockeyplayer.png'}
                    alt={'hockey player'}
                />
            </div>
        );
    }
}

export default HomeContainer;