import React, { Component } from 'react';
import { Route, Switch, Link } from 'react-router-dom';

// css
import './App.css';

// containers
import ManagersContainer from "./containers/ManagersContainer/ManagersContainer";
import HockeyPlayersContainer from "./containers/PlayersContainer/PlayersContainer";
import TeamsContainer from "./containers/TeamsContainer/TeamsContainer";
import HomeContainer from './containers/HomeContainer/HomeContainer';
import GamesContainer from "./containers/GamesContainer/GamesContainer";
import TeamDetailContainer from "./containers/TeamsContainer/TeamDetailContainer";
import AuthContainer from "./containers/AuthContainer/AuthContainer";
import TeamCreateContainer from "./containers/TeamsContainer/TeamCreateContainer";

// antd
import { Layout, Menu, Icon } from 'antd';
const { Header, Content, Footer, Sider } = Layout;

class App extends Component {
    state = {
        collapsed: false,
    };
    onCollapse = (collapsed) => {
        console.log(collapsed);
        this.setState({ collapsed });
    }
    render() {
        return (
            <Layout className="Layout">
                <Sider
                    collapsible
                    collapsed={this.state.collapsed}
                    onCollapse={this.onCollapse}>
                    <div className="logo" />
                    <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
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
                            <Link to="/hockeyplayers">
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
                    <Header className="Header"/>
                    <Content className="Content">
                        <Switch>
                            <Route path="/games" component={GamesContainer} />
                            <Route path="/hockeyplayers" component={HockeyPlayersContainer} />
                            <Route path="/teams/create" component={TeamCreateContainer}/>
                            <Route path="/teams/:id" component={TeamDetailContainer}/>
                            <Route path="/teams" component={TeamsContainer} />
                            <Route path="/managers" component={ManagersContainer} />
                            <Route path="/auth" component={AuthContainer} />
                            <Route path="/" exact component={HomeContainer} />
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

export default App;
