import React, { Component } from 'react';
import API from './Api.jsx';

const rest = require('../dist/restaurant2.jpg');

export default class Register extends Component {

    constructor(props) {
        super(props);
        this.handleChangeUsername = this.handleChangeUsername.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleChangeAddress = this.handleChangeAddress.bind(this);
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            username: '',
            password: '',
            address: '',
            email: ''
        }
    }

    handleChangeUsername(event) {
      this.setState({ username: event.target.value});
    }

    handleChangePassword(event) {
      this.setState({ password: event.target.value});
    }

    handleChangeAddress(event) {
      this.setState({ address: event.target.value});
    }

    handleChangeEmail(event) {
      this.setState({ email: event.target.value});
    }

    onSubmit(e) {
        e.preventDefault();
        const user = {
          username: this.state.username,
          password: this.state.password,
          address: this.state.address,
          email: this.state.email
        }

        this.state = {
          error: '',
        };
        API.post('/register',user)
          .then(response => this.props.history.push(`/`))
          .catch(error => this.setState({error: error.response.data.title}));

    }

    handleError() {
      if(this.state.error) {
        return(
        <div className="alert alert-danger col-12" role="alert">
          {this.state.error}
        </div>
        )
      }
    }

    render() {
        return (
          <div className="container" style={{width: '19rem'}}>
           <img src={rest}/>
            <div style={{marginTop: 50}}>
                <span className="container"><h3>Registrate en MorfiYa</h3></span>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>Username:  </label>
                        <input type="text" className="form-control" value={this.state.username}  onChange={event => this.handleChangeUsername(event)}/>
                    </div>
                    <div className="form-group">
                        <label>Password: </label>
                        <input type="password" className="form-control" value={this.state.password}  onChange={event => this.handleChangePassword(event)}/>
                    </div>
                    <div className="form-group">
                        <label>Address: </label>
                        <input type="text" className="form-control" value={this.state.address}  onChange={event => this.handleChangeAddress(event)}/>
                    </div>
                    <div className="form-group">
                        <label>Email: </label>
                        <input type="email" className="form-control" value={this.state.email}  onChange={event => this.handleChangeEmail(event)}/>
                    </div>
                    <div className="form-group row" style={{float: 'right'}}>
                        <div className="col-12" style={{marginTop:'55px'}}>
                          <input type="submit" value="Register" className="btn btn-primary"/>
                        </div>
                    </div>
                </form>
            </div>
            <div>
              {this.handleError()}
            </div>
          </div>
        )
    }
}
