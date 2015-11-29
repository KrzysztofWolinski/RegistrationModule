angular.module('Registration', [])
	.config(['$httpProvider',
		function($httpProvider) {
			'use strict';
			$httpProvider.defaults.timeout = 5000;
		}
	]);