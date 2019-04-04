// state variables
var login = false;


function select_option_discover(btn) {

    reset_option_discover();
    btn.className = 'disc_btn active';
}

function reset_option_discover() {
    var discover_options = document.getElementsByClassName("disc_btn");

    Array.prototype.filter.call(discover_options, function(btn){
        btn.className = 'disc_btn';
    });
}

function select_option_popular(btn) {

    reset_option_popular();
    btn.className = 'pop_btn active';
}

function reset_option_popular() {
    var pop_options = document.getElementsByClassName("pop_btn");

    Array.prototype.filter.call(pop_options, function(btn){
        btn.className = 'pop_btn';
    });
}

function select_option_weekly(btn) {

    reset_option_weekly();
    btn.className = 'weekly_btn active';
}

function reset_option_weekly() {
    var weekly_options = document.getElementsByClassName("weekly_btn");

    Array.prototype.filter.call(weekly_options, function(btn){
        btn.className = 'weekly_btn';
    });
}

function toggle_login(flag) {
    if(!flag) {
        login = false;
        document.getElementById("signup_title").className = 'active_cus';
        document.getElementById("login_title").className = '';
        document.getElementById("signup_extra").style.display = 'block';
        document.getElementById('login_btn').innerText = 'Sign up NOW';
    }else {
        login = true;
        document.getElementById("signup_title").className = '';
        document.getElementById("login_title").className = 'active_cus';
        document.getElementById("signup_extra").style.display = 'none';
        document.getElementById('login_btn').innerText = 'Login';
    }
}

function getUrlParam(parameter, defaultvalue){
    var urlparameter = defaultvalue;
    if(window.location.href.indexOf(parameter) > -1){
        urlparameter = getUrlVars()[parameter];
    }
    return urlparameter;
}

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

function go_to_login() {
    window.location.href = "/signup.html?login=true"
}

function expand_search() {
    $('#search_input_box').toggle();
    if(document.getElementById('search_input_box').style.display != 'none')
        document.getElementById('search_icon').style.color='blueviolet';
    else
        document.getElementById('search_icon').style.color='black';
}