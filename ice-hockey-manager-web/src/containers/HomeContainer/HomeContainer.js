import React, { Component } from 'react';
import { connect } from 'react-redux';

// actions
import * as actions from '../../store/actions/index';

// css
import './HomeContainer.css';

class HomeContainer extends Component {

    componentDidMount() {
        this.props.onSetAuthRedirectPath('/');
    }

    render() {
        return (
            <div>
                <h1>Ice Hockey Manager</h1>
                <h3>Manage your favorite hockey team!</h3>
                <img
                    src={'./assets/pics/hockeyplayer.png'}
                    alt={'hockey player'}
                />
            </div>
        );
    }
}

const mapDispatchToProps = dispatch => {
    return {
        onSetAuthRedirectPath: (path) => dispatch(actions.setAuthRedirectPath(path))
    };
};

export default connect( null, mapDispatchToProps )(HomeContainer);