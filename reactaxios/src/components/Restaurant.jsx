import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import API from './Api.jsx';
import Home from './Home.jsx';

export default class Restaurant extends Component {
  constructor(props) {
    super(props);
    this.state = {
      restaurantId: props.match.params.id,
      restaurantName:'',
      menues: [],
      idUsuario: props.location.state.idUsuario,
      username: props.location.state.username,
      listaPedidos: [],
      coor: props.location.state.coor
    };
  }

  componentDidMount() {
    API.get(`/restaurant/${this.state.restaurantId}`)
    .then((response) => {
     console.log(response);
     this.setState({restaurantName:response.name});
     this.setState({menues: response.menus});
   })
  .catch((error)=>{
     console.log(error);
  });
  }

  realizarPedido(pedido) {
    API.post(`/deliver/${this.state.idUsuario}`,pedido)
      .then((response) => {
       console.log(response);
       let path = `/home`;
       this.props.history.push(path,this.state);
     })
    .catch((error)=>{
       console.log(error);
    });
  }

  cancelar() {
    let path = `/home`;
    this.props.history.push(path,this.state);
  }

  armarPedido(e) {
    e.preventDefault();
    const costoPedido = this.calcularCostoPedido();
    const restaurantId = this.state.restaurantId;
    const nombreRestaurant = this.state.restaurantName;
    const listaPedidos = this.state.listaPedidos;
    const coordUsuario = this.armarCoor();
    var pedido  = {
            "restaurantId": restaurantId,
            "nombreRestaurant": nombreRestaurant,
            "menus": listaPedidos,
            "estado": "Pendiente",
            "costoTotalPedido": costoPedido,
            "tarjetaDeCredito": null,
            "destination": coordUsuario,
            "efectivo": null,
            "mercadoPago": null
    }
    this.realizarPedido(JSON.stringify(pedido));
  }

  armarCoor() {
    var coor = { "latitude": this.state.coor.latitude,"longitude": this.state.coor.longitude};
    return coor;
  }

  agregarAPedido(menuName,e) {
    e.preventDefault();
    const listaPedidosCopia = this.state.listaPedidos.slice();
    const menu = this.state.menues.find(function(element) {
      if(element.name == menuName){
        return element;
      }
    });
    if(menu.amount) {
      var menuNuevo = { "name": menu.name, "rawPrice": menu.rawPrice, "amount":menu.amount};
      listaPedidosCopia.push(menuNuevo);
      this.setState({listaPedidos:listaPedidosCopia}) ;
    }
  }

  calcularCostoPedido() {
    var costoTotal = 0;
    this.state.listaPedidos.forEach(function(element) {
      costoTotal += element.rawPrice * element.amount
    });

    return costoTotal;
  }

  createContent(menues) {
    const contenido = this.contentToCards(menues);
    return (
      <div className="container">
        <div className="row">
        {contenido}
        </div>
      </div>
    );
  }

guardarAmount(amount,menuName) {
      const menuesCopia = this.state.menues.slice();
      const menuModificado = this.modificarMenu(menuesCopia,menuName,amount);
      this.setState({menues:menuModificado}) ;
}

modificarMenu(menues,menuName,amount) {
  const menu = menues.find(function(element) {
      if(element.name == menuName) {
        return element;
      }
  });

  menu.amount = amount;

  return menues;

}

  contentToCards(menues) {
    return menues.map((menu,i) => <div className="list-inline-item col-12" key={i}> {this.createCard(menu)}</div>);
  }

  createCard(menu) {
    return (
      <div className="card mb-2" style={{height: '13rem'}}  key={menu}>
        <div className="card-body">
          <h4 className="card-title">{menu.name}</h4>
          <div className="card-text">
            <div className="row">
              <div className="col-7">
              {this.createRenderProducts(menu.products)}
              Total: $ {menu.rawPrice}
              </div>
              <div className = "col-3">
                <label> Amount </label>
                <input
                type="number"
                min="1"
                onChange = {event => this.guardarAmount(event.target.value,menu.name)}
                ></input>
              </div>
              <div className="col-2">
                  <div className = "row">
                     <button type = "button" className = "btn btn-success mt-3" onClick={e => this.agregarAPedido(menu.name,e)}>Agregar</button>
                  </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
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
      <div>
        <h4>{this.state.restaurantName}</h4>
        <div className="row">
          <div className="col-md-3 offset-md-9 mb-3">
            <div className="row">
              <div className="col-7">
              <button className="btn btn-primary" onClick={e => this.armarPedido(e)} style={{ display: (this.state.listaPedidos.length > 0 ? 'block' : 'none') }}>Realizar Pedido</button>
              </div>
              <div className="col-5">
                <button className="btn btn-danger" onClick={e => this.cancelar(e)} style={{ display: (this.state.listaPedidos.length > 0 ? 'block' : 'none') }}>Cancelar</button>
              </div>
            </div>
          </div>
        </div>
        {this.createContent(this.state.menues)}

      </div>
    );
}

}
