import React, { Component } from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Login from './Login.jsx';
import Home from './Home.jsx';
import Restaurant from './Restaurant.jsx';
import Search from './Search.jsx';
import Register from './Register.jsx';

import 'bootstrap/dist/css/bootstrap.min.css';

class App extends Component {
  componentDidMount() {
    document.title = 'Morfi Ya';
  }

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Router>
        <div className="container">
          <nav className="navbar navbar-light bg-light mb-4">
            <h1 className="navbar-brand" >Morfi Ya</h1>
          </nav>

          <Switch>
              <Route path="/search/:text" component={ Search } />
              <Route path='/home' component={ Home } />
              <Route path='/restaurant/:id' component = { Restaurant } />
              <Route exact path='/' component={ Login } />
              <Route exact path='/register' component={ Register } />
          </Switch>
        </div>
      </Router>
    );
  }
}

export default App;
