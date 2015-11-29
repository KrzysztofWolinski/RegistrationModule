'use strict';

angular.module('Registration')
	.service('RegistrationService', [
		'$q',
		'$http',
		'REGISTRATION_URL',
		function($q, $http, REGISTRATION_URL) {		

			var APPLICATION_JSON_VALUE = 'application/json;charset=UTF-8';

			var service = {
				submitForm: function submitForm(user) {
					var deferred = $q.defer();

					$http({
							method: 'POST',
							url: REGISTRATION_URL + '/register',
							headers: {
								'Content-Type': APPLICATION_JSON_VALUE
							},
							data: {
								username: user.username,
								password: user.password
							}
						})
						.success(function(data) {
							deferred.resolve(data);
						})
						.error(function() {
							deferred.reject();
						});

					return deferred.promise;
				}
			};

			return service;

		}
	]);