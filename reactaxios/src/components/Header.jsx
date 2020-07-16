import {Link} from 'react-router-dom';
import React, { Component } from 'react';

const jesus = require('../dist/jesusBailando.gif');
const user = require('../dist/user.jpg')


export default class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {
            idUsuario: props.idUsuario,
            username: props.username,
            toSearch: "",
            coor: props.coor
        };
    }

    handleChange(event) {
        this.setState({ toSearch: event.target.value });
    }

    handleSubmit() {
        this.props.history.push({ pathname: `/search/${this.state.toSearch}`, state: this.state });
    }

    goHome() {
        this.props.history.push(this.home());
    }

    home() {
        return `/home/${this.state.idUsuario}`;
    }

    render() {
        return (
        <React.Fragment>
          <nav className="navbar navbar-light bg-light row">
                <div className="col-8">
                  <div className="row">
                    <div className="col-10">
                      <input className="form-control" type="search" placeholder="Ingresar texto..." onChange={event=> this.handleChange(event)} />
                    </div>
                    <div className="col-2">
                      <button className="btn btn-outline-success my-2 my-sm-0" type="submit" onClick={()=> this.handleSubmit()}>Search</button>
                    </div>
                    <img src={jesus}/>
                  </div>
                </div>
                <div className="col-4">
                  <div className="row">
                    <div className="col-4">
                      <img src={user} style={{borderRadius:'50%'}}/>
                    </div>
                    <div className="col-8">
                      <div className="row">
                        <div className="col-6 mt-5">
                          <ul className="nav navbar-nav navbar-rigth">
                            <li className="nav-item justify-content-center">
                              <p className="nav-link"><span className="oi oi-person" /> {this.state.username} </p>
                              </li>
                          </ul>
                        </div>
                        <div className="col-6 mt-5">
                          <Link className="btn btn-danger" to="/">Salir</Link>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
          </nav>
        </React.Fragment>
        );
      }

}
