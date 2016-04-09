/*  Copyright © 2016- shoribu_jsn All Rights Reserved. */
var User = function(cont) {
	var self = this;
	self.Name = ko.observable(cont ? cont.Name : null);
};

var AuthorizationViewModel = function() {
	var self = this;
	self.LoggedInUser = new User();

	$.ajax("../api/authorization/user", {
		success: function (data, textStatus, jqXHR) {
			self.LoggedInUser.Name(data.Name);
		}
	});
};

var viewModel = AuthorizationViewModel();

$(document).ready(function() {
	ko.applyBindings(viewModel);
});