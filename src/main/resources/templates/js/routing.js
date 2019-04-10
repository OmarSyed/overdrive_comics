/*
 * This file is meant to describe the front-end routing
 * for the application
 */
window.addEventListener('load', () => {
	
	//App div
	const app = $('#app');
	
	const router = new Router({
	    mode: 'history',
	    page404: (path) => {
	      const html = errorTemplate({
	        color: 'yellow',
	        title: 'Error 404 - Page NOT Found!',
	        message: `The path '/${path}' does not exist on this site`,
	      });
	      app.html(html);
	    },
	});
}