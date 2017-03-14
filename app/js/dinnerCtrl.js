// Dinner controller that we use whenever we have view that needs to 
// display or modify the dinner menu
dinnerPlannerApp.controller('DinnerCtrl', function ($scope,Dinner,$cookies) {

  $scope.numberOfGuests = Dinner.getNumberOfGuests();

  $scope.setNumberOfGuest = function(number){
    Dinner.setNumberOfGuests(number);
  }

  $scope.getNumberOfGuests = function() {
    return Dinner.getNumberOfGuests();
  }

  $scope.addGuest = function() {
    $scope.numberOfGuests++;
    $scope.setNumberOfGuest($scope.numberOfGuests);
      //$cookies.NumberOfGuest = numberOfGuests;
  }

    $scope.removeGuest = function() {
        $scope.numberOfGuests--;
        $scope.setNumberOfGuest(numberOfGuests);
    }

    $scope.function1=function(){
        //your code
        $scope.selectedMenu = Dinner.getFullMenu();
    }


    $scope.addDishtoMenu_Dishhtml = function(selecteddish , selected_DishType){
        Dinner.addDishToMenu($scope.selecteddish , $scope.selected_DishType);
        $scope.selectedMenu = Dinner.getFullMenu();

    }

    //console.log($scope.selectedMenu[0]);

    // TODO in Lab 5: Implement the methods to get the dinner menu
  // add dish to menu and get total menu price

    $scope.$on('menuUpdated', function() {
        $scope.selectedMenu = Dinner.getFullMenu();
    });
});

