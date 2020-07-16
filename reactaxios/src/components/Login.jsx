import React, { Component } from 'react';
import API from './Api.jsx';

const rest = require('../dist/restaurant2.jpg');

export default class Login extends Component {

    constructor(props) {
        super(props);
        this.handleChangeUsername = this.handleChangeUsername.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            username: '',
            password: '',
            idUsuario: '',
            coor: {}
        }
    }

    handleChangeUsername(event) {
      this.setState({ username: event.target.value});
    }

    handleChangePassword(event) {
      this.setState({ password: event.target.value});
    }

    onSubmit(e) {
        e.preventDefault();
        const user = {
          username: this.state.username,
          password: this.state.password,
        }

        this.state = {
          error: '',
        };
        API.post('/login',user)
          .then(response => {
            this.setState({coor:response.coor,idUsuario:response.id})
            this.props.history.push({pathname:`/home`,state:this.state})})
          .catch(error => this.setState({error: error.response.data.title}));

    }

    handleError() {
      if(this.state.error) {
        return(
        <div className="alert alert-danger col-12" style={{padding:'10px'}} role="alert">
          {this.state.error}
        </div>
        )
      }
    }

    registrar(e) {
      e.preventDefault();
      this.props.history.push(`/register`);
    }

    render() {
        return (
          <div className="container" style={{width: '19rem'}}>
           <img src={rest}/>
            <div style={{marginTop: 50}}>
                <span className="container"><h3>Bienvenido a MorfiYa</h3></span>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>Username:  </label>
                        <input type="text" className="form-control" value={this.state.username}  onChange={event => this.handleChangeUsername(event)}/>
                    </div>
                    <div className="form-group">
                        <label>Password: </label>
                        <input type="password" className="form-control" value={this.state.password}  onChange={event => this.handleChangePassword(event)}/>
                    </div>
                    <div className="form-group row" style={{float: 'right'}}>
                        <div className="col-12" style={{marginTop:'55px'}}>
                          <input type="submit" value="Log in" className="btn btn-primary"/>
                          <input type="submit" value="Register" onClick={e => this.registrar(e)} className="btn btn-danger mr-3" style={{float: 'left'}}/>
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
