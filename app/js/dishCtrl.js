// Dinner controller that we use whenever we want to display detailed
// information for one dish

dinnerPlannerApp.controller('DishCtrl', function ($scope,$routeParams,Dinner) {
  
  // TODO in Lab 5: you need to get the dish according to the routing parameter
  // $routingParams.paramName
  // Check the app.js to figure out what is the paramName in this case

    $scope.selected_DishId = $routeParams.dishId;
    $scope.selected_DishType = $routeParams.type;
    $scope.selecteddish = undefined;
    $scope.selecteddish_Price;
    var i=0;

    console.log($scope.selected_DishType);

        Dinner.Dish.get({id: $scope.selected_DishId},function(data){
            console.log(JSON.stringify(data));
            $scope.selecteddish = data;
            console.log($scope.selecteddish);
            $scope.selecteddish_Price = Dinner.getDishPrice($scope.selecteddish);

        },function(error){
            $scope.status = "There was an error";
        });

    $scope.addDishtoMenu_Dishhtml = function(selecteddish , selected_DishType){
        Dinner.addDishToMenu($scope.selecteddish , $scope.selected_DishType);
        //$scope.selectedMenu = Dinner.getFullMenu();

    }


});