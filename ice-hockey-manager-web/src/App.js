import React, { Component } from 'react';
import { Route, Switch, Link, withRouter } from 'react-router-dom';
import { connect } from 'react-redux';

// css
import './App.css';

// components
import Logout from './components/Logout';

// containers
import ManagersContainer from "./containers/ManagersContainer/ManagersContainer";
import ManagerEditContainer from "./containers/ManagersContainer/ManagerEditContainer";
import PlayersContainer from "./containers/PlayersContainer/PlayersContainer";
import TeamsContainer from "./containers/TeamsContainer/TeamsContainer";
import HomeContainer from './containers/HomeContainer/HomeContainer';
import GamesContainer from "./containers/GamesContainer/GamesContainer";
import GamesCreateContainer from "./containers/GamesContainer/GamesCreateContainer";
import GamesChangeStartTimeContainer from "./containers/GamesContainer/GamesChangeStartTimeContainer";
import TeamDetailContainer from "./containers/TeamsContainer/TeamDetailContainer";
import AuthContainer from "./containers/AuthContainer/AuthContainer";
import TeamCreateContainer from "./containers/TeamsContainer/TeamCreateContainer";
import PlayerCreateContainer from "./containers/PlayersContainer/PlayerCreateContainer";
import NotFound from "./components/NotFound";

// antd
import { Layout, Menu, Icon } from 'antd';
const { Header, Content, Footer, Sider } = Layout;

class App extends Component {

    state = {
        current: '1'
    };

    componentWillMount() {
        const location = this.props.location.pathname;
        if (location === '/') {
            return '1';
        }
        if (location.includes('games')) {
            this.setCurrent('2');
        }
        if (location.includes('players')) {
            this.setCurrent('3');
        }
        if (location.includes('teams')) {
            this.setCurrent('4');
        }
        if (location.includes('managers')) {
            this.setCurrent('5');
        }
    }

    setCurrent(current) {
        this.setState({current: current});
    }

    handleClick = (e) => {
        this.setState({
            current: e.key,
        });
    };

    isAuthenticated() {
        return this.props.isAuthenticated ? <Link to="/logout"><Icon type="logout"  style={{ fontSize: 25}} /></Link> :
            <Link to="/auth"><Icon type="login"  style={{ fontSize: 25}} /></Link>;
    }

    render() {
        return (
            <Layout className="Layout">
                <Sider
                    collapsible>
                    <div className="logo" />
                    <Menu theme="dark"
                          onClick={this.handleClick}
                          selectedKeys={[this.state.current]}
                          mode="inline">
                        <Menu.Item key="1">
                            <Link to="/">
                                <Icon type="home" />
                                <span>Home</span>
                            </Link>
                        </Menu.Item>
                        <Menu.Item key="2">
                            <Link to="/games">
                                <Icon type="profile" />
                                <span>Games</span>
                            </Link>
                        </Menu.Item>
                        <Menu.Item key="3">
                            <Link to="/players">
                                <Icon type="user-add" />
                                <span>Hockey Players</span>
                            </Link>
                        </Menu.Item>
                        <Menu.Item key="4">
                            <Link to="/teams">
                                <Icon type="team" />
                                <span>Teams</span>
                            </Link>
                        </Menu.Item>
                        <Menu.Item key="5">
                            <Link to="/managers">
                                <Icon type="contacts" />
                                <span>Managers</span>
                            </Link>
                        </Menu.Item>
                    </Menu>
                </Sider>
                <Layout>
                    <Header className="Header">
                        <div className="auth-panel">
                            {this.isAuthenticated()}
                        </div>
                    </Header>
                    <Content className="Content">
                        <Switch>
                            <Route path="/players/create" exact component={PlayerCreateContainer}/>
                            <Route path="/players" exact component={PlayersContainer} />
                            <Route path="/games" exact component={GamesContainer} />
                            <Route path="/games/create" exact component={GamesCreateContainer} />
                            <Route path="/games/edit/:id" exact component={GamesChangeStartTimeContainer} />
                            <Route path="/teams/create" exact component={TeamCreateContainer}/>
                            <Route path="/teams/:id" exact component={TeamDetailContainer}/>
                            <Route path="/teams" exact component={TeamsContainer} />
                            <Route path="/managers/changepassword" exact component={ManagerEditContainer} />
                            <Route path="/managers" exact component={ManagersContainer} />
                            <Route path="/auth" exact component={AuthContainer} />
                            <Route path="/logout" exact component={Logout} />
                            <Route path="/" exact component={HomeContainer} />
                            <Route path="/" component={NotFound} />
                        </Switch>
                    </Content>
                    <Footer style={{ textAlign: 'center' }}>
                        Ice Hockey Manager ©2018 Created by <a href={'https://github.com/m-barnas/ice-hockey-manager'}>Gold Team</a>
                    </Footer>
                </Layout>
            </Layout>
        );
    }
}

const mapStateToProps = state =>  {
    return {
        isAuthenticated: state.auth.token !== null,
        currentPage: state.menu.currentPage
    };
};

export default withRouter(connect( mapStateToProps )(App));
