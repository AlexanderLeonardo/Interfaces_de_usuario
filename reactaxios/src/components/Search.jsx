//import '../dist/css/home.css';
//import 'bootstrap/dist/css/bootstrap.min.css';
//import 'bootstrap/dist/js/bootstrap.bundle.min.js';
//import 'open-iconic/font/css/open-iconic-bootstrap.min.css';
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import API from './Api.jsx';
import Home from './Home.jsx';
import Header from './Header.jsx';


export default class Search extends Component {
    constructor(props) {
      super(props);
      this.state = {
        idUsuario: props.location.state.idUsuario,
        username: props.location.state.username,
        contenidos: {},
        coor: props.location.state.coor
      };
    }

    componentDidMount() {
          this.search(this.props.match.params.text);
        }

    search(nextProps) {
      if(nextProps) {
      API.get(`/search?text=${nextProps}`)
      .then((response) => {
       console.log(response);
       this.setState({contenidos: response})
     })
    .catch((error)=>{
       console.log(error);
    });
  } else {
    API.get(`/search`)
    .then((response) => {
     console.log(response);
     this.setState({contenidos: response})
   })
  .catch((error)=>{
     console.log(error);
  });
    }
  }

    componentWillReceiveProps(nextProps) {
    this.search(nextProps.match.params.text);
  }

    createTitleAndContent(restaurants,menues) {
      const contenidoRestaurants = this.contentToCardsRestaurants(restaurants);
      const contenidoMenues = this.contentToCardsMenues(menues)
        return (
          <div className="container">
            <h4 className="alert alert-primary">Restaurantes:</h4>
            <div className="row">
              {contenidoRestaurants}
            </div>
            <h4 className="alert alert-primary">Menues:</h4>
            <div className="row">
              {contenidoMenues}
            </div>
          </div>
        );
    }

    contentToCardsRestaurants(restaurants) {
      if(restaurants){
        return restaurants.map((restaurant,i) => <div className="list-inline-item col-12" key={i}> {this.createCardRestaurant(restaurant)}</div>);
      } else {
        return (
          <div className="container">
            <p>No existen restaurantes para esa busqueda</p>
          </div>
        );
      }
    }

    createCardRestaurant(restaurant) {
      return(
      <div className="card mb-2" style={{height: '11rem'}}  key={restaurant}>
        <div className="card-body">
          <h5 className="card-title"><Link to={{pathname:`/restaurant/${restaurant.id}`,state: this.state}} activeclassname="active">Restaurant: {restaurant.name}</Link></h5>
          <div className="card-text">
            <div className="row">
              <div className="col-10">
              {restaurant.description}
              </div>
            </div>
          </div>
        </div>
      </div>
     )
    }

    contentToCardsMenues(menues) {
      if(menues){
        return menues.map((menu,i) => <div className="list-inline-item col-12" key={i}> {this.createCardMenu(menu)}</div>);
      } else {
        return (
          <div className="container">
            <p>No existen menues para esa busqueda</p>
          </div>
        );
      }
    }

    createCardMenu(menu) {
        return(
            <div className="card mb-2" style={{height: '13rem'}}  key={menu}>
              <div className="card-body">
                <h5 className="card-title"> Menu: {menu.name}</h5>
                <div className="card-text">
                  <div className="row">
                    <div className="col-10">
                    {this.createRenderProducts(menu.products)}
                    Total: $ {menu.rawPrice}
                    </div>
                  </div>
                </div>
              </div>
            </div>
           )
    }

    createRenderProducts(productos) {
        return productos.map((producto,i) => <div className="col-12" key={i}> {this.createProductItem(producto)} </div>);
    }

    createProductItem(producto) {
      return (
        <div>
          <p>{producto.amount} x {producto.name}</p>
        </div>
      );
    }







    render() {
        return (
          <React.Fragment>
             <div>
             <Header idUsuario={this.state.idUsuario} username={this.state.username} {...this.props} />
              {this.createTitleAndContent(this.state.contenidos.restaurants, this.state.contenidos.menues)}
            </div>
            </React.Fragment>
        );
    }


    }
