import React from 'react'
import Stomp from 'stompjs'
import SockJS from 'sockjs-client'

export default class Websocket extends React.Component {

  componentWillMount() {
    this.initializeWebSocketConnection()
  }

  initializeWebSocketConnection = () => {
    let ws = new SockJS('/ws_0001');
    let stompClient = Stomp.over(ws);
    console.log(localStorage.getItem("accessToken"));
    stompClient.connect({Authorization: "Bearer " + localStorage.getItem("accessToken")}, function (frame) {
      stompClient.subscribe('/front/endpoint1', function (resp) {
        this.processResponse(resp.body);
      });
    });
  };

  processResponse = body => {
    const object = JSON.parse(body);
    const html = "<tr><td>" + object.content + "</td><td>" + object.id + "</td></tr>";
    document.getElementById("cont").innerHTML.append(html);
  }

  /*connect = () => {
    const socket = new SockJS('http://localhost:8080/ws_0001');
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
      stompClient.subscribe('/front/endpoint1', function (resp) {
        this.processResponse(resp.body);
      });
    });
  };

  processResponse = body => {
    const object = JSON.parse(body);
    const html = "<tr><td>" + object.content + "</td><td>" + object.id + "</td></tr>";
    document.getElementById("cont").innerHTML.append(html);
  }*/

  render () {

    return (
      <div key={Websocket.id}>
        <div id="cont" className='container'>

        </div>

      </div>
    )
  }
}