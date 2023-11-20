/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import './Login.css';
import { PageTitle } from '../../components/page';
import { IconButton } from '../../../core/button/IconButton';

export default class Login extends Component {
    constructor(username, password) {
        super();
        this.username=username;
        this.password=password;
    }

    render() {
        let loginfun = this.login.bind(this);
        // se bisogna caricare la lista nella home non serve caricare le tipologie, ramo else
          return html`
          <div id="login" class="modal">
          <div class="large-6 columns">
  
            <form id="login-form" class="modal-content animate">

                <div class="container">
                <b>Username</b>
                <input id="usname" type="text" placeholder="Username" required>

                <b>Password</b>
                <input id="psswd" type="password" placeholder="Password" required>
                <input type="submit" class="btn btn-info" value="Login">
                
                </div>

              
            </form>
            </div>
            </div>
            `;
        
    }

    after_render() {
        document.getElementById('login-form').addEventListener('submit', this.login.bind(this));
    }

    login(event){
        event.preventDefault();
        const loginForm = document.getElementById("login-form");
        const username = loginForm.usname.value;
        const password = loginForm.psswd.value;
        if(username === this.username && password === this.password){
            // alert("You have successfully logged in.");
            sessionStorage.setItem("islogged", "logged");
            location.reload();
            
        } else {
            alert("Credenziali errate");
        }
    }

}