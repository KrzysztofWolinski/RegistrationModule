'use strict';

angular.module('Registration')
	.controller('RegistrationController', [
		'$scope',
		'RegistrationService',
		'RESPONSE_STATUS',
		function($scope, RegistrationService, RESPONSE_STATUS) {		

			$scope.user = {
				username: '',
				password: '',
				repeatedPassword: ''
			};

			$scope.states = {
				ok: 'ok',
				loading: 'loading',
				error: 'error',
				success: 'success'
			};

			$scope.state = $scope.states.ok;
			$scope.errorMessages = [];

			$scope.passwordsMatch = function() {
				return $scope.user.password === $scope.user.repeatedPassword;
			};

			$scope.register = function() {
				$scope.state = $scope.states.loading;			
				RegistrationService.submitForm($scope.user).then(function(response) {
					
					if (response.status === RESPONSE_STATUS.OK) {
						$scope.state = $scope.states.success;
						$scope.errorMessages = [];
					} else {
						$scope.state = $scope.states.error;
						$scope.errorMessages = response.messages;
					}

				}, function() {
					$scope.state = $scope.states.error;
				});

			};

		}
	]);