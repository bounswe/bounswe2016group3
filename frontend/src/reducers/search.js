var search = function(prev=[], event) {
	switch(event.type) {
		case 'SEARCHMEAL_LOADED':
			return event.data;
		default:
			return prev;
	}
}

export { search };