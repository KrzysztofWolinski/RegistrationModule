'use strict';

angular.module('Registration')
	.service('ValidationService', [
		function() {		

			var service = {
				validate: {
					lengthMin: lengthMin,
					regexp: regexp,
					hasUppercaseLetters: hasUppercaseLetters,
					hasLowercaseLetters: hasLowercaseLetters,
					hasDigits: hasDigits
				}
			};

			function lengthMin(model, minLength) {
				return true;
			}

			function regexp(model, regexp) {
				return true;
			}

			function hasUppercaseLetters(model) {
				return true;
			}

			function hasLowercaseLetters(model) {
				return true;
			}

			function hasDigits(model) {
				return true;
			}

			return service;

		}
	]);