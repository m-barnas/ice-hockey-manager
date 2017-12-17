import React, {Component} from 'react';

import axios from '../../axios';
import {Row} from 'antd';
import {Link} from 'react-router-dom';

class TeamDetailContainer extends Component {

    state = {
        team: {},
        humanPlayer: {},
        loading: true,
        isHumanPlayer: false,
    };

    componentDidMount() {
        axios.get('/teams/' + this.props.match.params.id)
            .then(responseTeam => {
                if (responseTeam.data.humanPlayerId != null) {
                    axios.get('/managers/' + responseTeam.data.humanPlayerId)
                        .then(responseHumanPlayer => {
                            this.setState({
                                team: responseTeam.data,
                                humanPlayer: responseHumanPlayer.data,
                                loading: false,
                                isHumanPlayer: true
                            });
                        });
                } else {
                    this.setState({
                        team: responseTeam.data,
                        loading: false
                    });
                }
            })
            .catch(error => {
                console.log(error);
            });
    }

    static transformCountryLabel(country) {
        switch (country) {
            case 'FINLAND':
                return 'Finland';
            case 'CZECH_REPUBLIC':
                return 'Czech Republic';
            case  'SLOVAKIA' :
                return 'Slovakia';
            case 'FRANCE':
                return 'France';
            case 'ENGLAND' :
                return 'England';
            case 'Germany' :
                return 'Germany';
            default:
                return 'Uknown team';
        }
    }

    render() {
        let humanPlayer = this.state.isHumanPlayer
            ? <Link to={'/managers/' + this.state.humanPlayer.id}>{this.state.humanPlayer.username}</Link>
            : 'Not added yet';

        return (
            <div>
                <Row><h2>{this.state.team.name}</h2></Row>
                <Row><h3>Competition
                    country: {TeamDetailContainer.transformCountryLabel(this.state.team.competitionCountry)}</h3>
                </Row>
                <Row><h3>Budget: {this.state.team.budget}</h3></Row>
                <Row><h3>Manager: {humanPlayer}</h3></Row>
            </div>
        );
    }

}

export default TeamDetailContainer;