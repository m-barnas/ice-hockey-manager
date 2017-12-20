import React, {Component} from 'react';

import axios from '../../axios';
import {Link, Redirect} from 'react-router-dom';

import {Form, Button, DatePicker} from 'antd';

const FormItem = Form.Item;


class GamesChangeStartTimeContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            game: {},
            redirect: false,
            loaded: false
        };
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    componentWillMount() {
        axios.get('/games/' + this.props.match.params.id)
            .then(response => {
                this.setState({
                    game: response.data,
                    loaded: true,
                });
            })
            .catch(error => {
                console.log(error);
            });
    }

    handleSubmit(e) {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
            console.log("values: ", values);
                axios.put('/games/' + this.props.match.params.id, values)
                    .then(response => {
                        this.setState({
                            redirect: true
                        });
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }
        });
    }

    handleConfirmBlur(e) {
        const value = e.target.value;
        this.setState({confirmDirty: this.state.confirmDirty || !!value});
    }

    render() {
        if (this.state.redirect){
           return <Redirect to="/games"/>;
        }
        const {getFieldDecorator} = this.props.form;

        const formItemLayout = {
            labelCol: {
                xs: {span: 24},
                sm: {span: 4},
            },
            wrapperCol: {
                xs: {span: 24},
                sm: {span: 4},
            },
        };
        const tailFormItemLayout = {
            wrapperCol: {
                xs: {
                    span: 24,
                    offset: 0,
                },
                sm: {
                    span: 16,
                    offset: 4,
                },
            },
        };

        if (!this.state.loaded) return <div />;

        return (
            <div>
            <h3>Hockey game {this.state.game.firstTeamDto.name} vs {this.state.game.secondTeamDto.name}</h3>
            <Form onSubmit={this.handleSubmit}>
                <FormItem
                    {...formItemLayout}
                    label="New start time"
                >
                    {getFieldDecorator('startTime', {
                        rules: [
                            {required: true, message: 'Please input start time.'}
                        ],
                    })(
                        <DatePicker showTime format="YYYY-MM-DD HH:mm:ss" />
                    )}

                </FormItem>

                <FormItem {...tailFormItemLayout}>
                    <Link to={'/games'}><Button style={{marginRight: 1 + 'em'}}
                            type="secondary"
                    >Back</Button></Link>
                    <Button type="primary" htmlType="submit">Save</Button>
                </FormItem>
            </Form>

            </div>
        );
    }
}

const WrappedChangeGame = Form.create()(GamesChangeStartTimeContainer);

export default WrappedChangeGame;