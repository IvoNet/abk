/* 
 * Copyright (C) 2014 Philippe Tjon-A-Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
'use strict';

/**
 * @ngdoc function
 * @name abkClientApp.controller:TransactionCtrl
 * @description
 * # TransactionCtrl
 * Controller of the abkClientApp
 */
function TransactionCtrl($scope, $q, currentDate, transactionsService, costCentersService) {
    $scope.data = undefined;
    $scope.showall = false;
    $scope.range = currentDate.range();

    $scope.previous = function () {
        $scope.range.start.setMonth($scope.range.start.getMonth() - 1);
        $scope.range.end.setMonth($scope.range.end.getMonth() - 1);
        $scope.data = undefined;

        retrieveData();
    };

    $scope.next = function () {
        $scope.range.start.setMonth($scope.range.start.getMonth() + 1);
        $scope.range.end.setMonth($scope.range.end.getMonth() + 1);
        $scope.data = undefined;

        retrieveData();
    };


    var processCostcenters = function (e) {
        if (e.filter) {
            e.filter = new RegExp(e.filter.trim(), 'i');
        }
        if (e.list) {
            e.list.forEach(processCostcenters);
        }
    };

    var retrieveData = function () {
        $q.all([transactionsService.get({q: 'date=[' + $scope.range.start.toJSON() + ' ' + $scope.range.end.toJSON() + ']', limit: 9999,
                fields: 'date,debitCreditIndicator,amount,description,contraAccountName', orderby: 'date desc'}).$promise,
            costCentersService.get({expand: 3}).$promise]).then(function (result) {

            $scope.data = result[0];
            var costcenters = result[1];

            costcenters.list.forEach(processCostcenters);



            $scope.data.list.forEach(function (t) {
                t.matched = false;
                if (t.debitCreditIndicator === 'debit') {
                    t.amount = {amount: -parseFloat(t.amount)};
                } else {
                    t.amount = {amount: parseFloat(t.amount)};
                }
                costcenters.list.forEach(function (c) {
                    if (c.filter && 
                            (c.filter.test(t.description) || c.filter.test(t.contraAccountName))) {
                        t.matched = true;
                        t.costcenter = c.name;

                    }
                    if (c.list) {
                        c.list.forEach(function (sc) {
                            if (sc.filter && 
                                    (sc.filter.test(t.description) || sc.filter.test(t.contraAccountName))) {
                                t.matched = true;
                                t.costcenter = sc.name;

                            }
                        });
                    }
                });
            });
        });
    };

    retrieveData();
}