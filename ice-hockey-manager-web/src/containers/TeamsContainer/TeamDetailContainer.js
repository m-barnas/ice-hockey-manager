import React, {Component} from 'react';

import axios from '../../axios';
import {Row, Col} from 'antd';

class TeamDetailContainer extends Component {

    state = {
        team: {},
        humanPlayer: {},
        loading: true
    };

    componentDidMount() {
        axios.get('/teams/' + this.props.match.params.id)
            .then(responseTeam => {

                axios.get('/managers/' + responseTeam.data.humanPlayerId)
                    .then(responseHumanPlayer => {
                        console.log(responseHumanPlayer);
                        console.log(responseHumanPlayer.data);
                        this.setState({
                            team: responseTeam.data,
                            humanPlayer: responseHumanPlayer.data,
                            loading: false
                        });
                    });
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
        }
    }

    render() {
        console.log(this.state);
        return (
            <div>
                <Row><h2>{this.state.team.name}</h2></Row>
                <Row><h3>Competition
                    country: {TeamDetailContainer.transformCountryLabel(this.state.team.competitionCountry)}</h3>
                </Row>
                <Row><h3>Budget: {this.state.team.budget}</h3></Row>
                <Row><h3>Manager: <a href={'/managers/'+ this.state.humanPlayer.id}>{this.state.humanPlayer.username}</a></h3></Row>
            </div>
        );
    }

}

export default TeamDetailContainer;