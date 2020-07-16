import React, { Component } from 'react';
import API from './Api.jsx';
import Header from './Header.jsx';
import { Link } from 'react-router-dom';

export default class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      idUsuario: props.location.state.idUsuario,
      username: props.location.state.username,
      listaPedidos: [],
      coor: props.location.state.coor
    };
  }

  componentDidMount() {
    API.get(`/users/${this.state.idUsuario}?include=pedidos`)
    .then((response) => {
     console.log(response);
     this.setState({listaPedidos: response.pedidos})
   })
  .catch((error)=>{
     console.log(error);
  });
  }

  createTitleAndContent(pedidos) {
    const contenido = this.contentToCards(pedidos);
    return (
      <div className="container">
        <h4 className="alert alert-primary">Pedidos</h4>
        <div className="row">
        {contenido}
        </div>
      </div>
    );
  }

  contentToCards(pedidos) {
    return pedidos.map((pedido,i) => <div className="list-inline-item col-12" key={i}> {this.createCard(pedido)}</div>);
  }

  createCard(pedido) {
    return (
      <div className="card mb-2" style={{height: '11rem'}}  key={pedido}>
        <div className="card-body">
          <h5 className="card-title"><Link to={{pathname:`/restaurant/${pedido.restaurantId}`,state: this.state}} activeclassname="active">Restaurant: {pedido.nombreRestaurant}</Link></h5>
          <div className="card-text">
            <div className="row">
              <div className="col-10">
              {this.createRenderMenues(pedido.menus)}
              </div>
              <div className="col-2">
                {pedido.estado}
              </div>
              <div className="col-6">
                Total: ${pedido.costoTotalPedido}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  createRenderMenues(menues) {
    return menues.map((menu,i) => <div className="col-12" key={i}> {this.createMenuItem(menu)} </div>);
  }

  createMenuItem(menu) {
    return (
      <div>
        <p>{menu.amount} x {menu.name} (${menu.rawPrice})</p>
      </div>
    );
  }

  render() {
    return (
      <React.Fragment>
       <div>
       <Header idUsuario={this.state.idUsuario} username={this.state.username} coor={this.state.coor} {...this.props} />
        {this.createTitleAndContent(this.state.listaPedidos)}
      </div>
      </React.Fragment>
    );
}
}
