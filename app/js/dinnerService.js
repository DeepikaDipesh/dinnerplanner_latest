// Here we create an Angular service that we will use for our 
// model. In your controllers (or other services) you can include the
// dependency on any service you need. Angular will insure that the
// service is created first time it is needed and then just reuse it
// the next time.
dinnerPlannerApp.factory('Dinner',function ($resource,$cookies,$rootScope) {
    var that = this;

 /* var numberOfGuest = 0;


  this.setNumberOfGuests = function(num) {
    numberOfGuest = num;
  }

  this.getNumberOfGuests = function() {
    return numberOfGuest;
  }*/
    //function that returns all dishes of specific type (i.e. "starter", "main dish" or "dessert")
    //you can use the filter argument to filter out the dish by name or ingredient (use for search)



  // TODO in Lab 5: Add your model code from previous labs
  // feel free to remove above example code
  // you will need to modify the model (getDish and getAllDishes)
  // a bit to take the advantage of Angular resource service
  // check lab 5 instructions for details
    //DinnerModel Object constructor

        var menu = {};
        var cookiesMenuId = [];
        var dishesRetrievedBasedOnCookies = [];
        var numberOfGuests = 0;

        if ($cookies.get("numberOfGuests") === undefined){
            $cookies.put("numberOfGuests",0);
        } else{
            numberOfGuests = $cookies.get('numberOfGuests');
        }





    this.setNumberOfGuests = function(num) {
            if(num>0) {
                numberOfGuests = num;
                $cookies.numberOfGuests=parseInt(num);
                $cookies.put('numberOfGuests',num);
               // notifyObservers();
            }
        }

        // should return
        this.getNumberOfGuests = function() {
            return numberOfGuests;
            //return $cookies.numberOfGuests;
        }

        //Returns the dish that is on the menu for selected type
        this.getSelectedDish = function(type) {
            return menu[type];
        }

        //Returns all the dishes on the menu.
        this.getFullMenu = function() {
            var menuDishes = [];
            for(key in menu) {
                menuDishes.push(menu[key]);
            }
            return menuDishes;
        }

        //Returns all ingredients for all the dishes on the menu.
        this.getAllIngredients = function() {
            var ingredients = {};
            for(dishKey in menu) {
                var dish = menu[dishKey];
                for(ingKey in dish.extendedIngredients) {
                    var ingredient = dish.extendedIngredients[ingKey];
                    if(ingredients[ingredient.id] !== undefined) {
                        ingredients[ingredient.id].amount += ingredient.amount;
                    } else {
                        ingredients[ingredient.id] = ingredient;
                    }
                }
            }
            return ingredients;
        }

        //Returns the total price of the menu (all the ingredients multiplied by number of guests).
        this.getTotalMenuPrice = function() {
            var ingredients = this.getAllIngredients();
            var sum = 0.;
            for(key in ingredients) {
                sum += parseFloat(ingredients[key].amount) * this.getNumberOfGuests();
            }
            return sum;
        }

        //Adds the passed dish to the menu. If the dish of that type already exists on the menu
        //it is removed from the menu and the new one added.
        this.addDishToMenu = function(dish, type) {
            dish['type']=type;
            menu[dish.type] = dish;
            dinnerMenuInCookies[type]=dish.id
            $cookies.putObject("dinnerMenuInCookies",dinnerMenuInCookies);
            that.broadcastItem();
          //  console.log(cookiesMenu);
        }

        //Removes dish from menu
        this.removeDishFromMenu = function(dish) {
            delete menu[dish.type];
          //  notifyObservers();
        }

        //function that returns all dishes of specific type (i.e. "starter", "main dish" or "dessert")
        //you can use the filter argument to filter out the dish by name or ingredient (use for search)
        //if you don't pass any filter all the dishes will be returned
        this.DishSearch = $resource('https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search',{},{
            get: {
                headers: {
                    'X-Mashape-Key': 'Qu9grxVNWpmshA4Kl9pTwyiJxVGUp1lKzrZjsnghQMkFkfA4LB'
                }
            }
        });
        this.Dish = $resource('https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/:id/information',{},{
            get: {
                headers: {
                    'X-Mashape-Key': 'Qu9grxVNWpmshA4Kl9pTwyiJxVGUp1lKzrZjsnghQMkFkfA4LB'
                }
            }
        });

        //function that returns a price of dish given by ID
        this.getDishPrice = function(dish) {
            var ingredients = dish.extendedIngredients;
            var price = 0;
            for (var i = 0; i < ingredients.length; i++) {
                price += ingredients[i].amount;
            };
            return price;
        }

        //function that returns possible dish types
        this.getDishTypes = function () {
            return ["Appetizer", "Main Course", "Dessert"];
        }

    var dinnerMenuInCookies = $cookies.getObject("dinnerMenuInCookies");

    if(typeof dinnerMenuInCookies != "undefined" && dinnerMenuInCookies != null){
        var countOfDishes = 0;
        var countOfDishesRetrievedFromAPI=0;
        for (dishType in dinnerMenuInCookies) {
            countOfDishes++;
            var dishID;
            var dishType=dishType;
            dishID = parseInt(dinnerMenuInCookies[dishType]);
            this.Dish.get({id:dishID},function(data) {
                dishesRetrievedBasedOnCookies.push(data);
                countOfDishesRetrievedFromAPI++;
                if(countOfDishesRetrievedFromAPI==countOfDishes){
                    convertDishesRetrievedBasedOnCookiesToDish();
                }

            }, function(error){
                // do noconsthing if error
                console.log(error);
            });
        }
    }
    else{
        dinnerMenuInCookies={};
    }
    // Angular service needs to return an object that has all the
  // methods created in it. You can consider that this is instead
  // of calling var model = new DinnerModel() we did in the previous labs
  // This is because Angular takes care of creating it when needed.
   function  convertDishesRetrievedBasedOnCookiesToDish(){

        var inverteddinnerMenuInCookies = {};
        for (dishType in dinnerMenuInCookies) {
           inverteddinnerMenuInCookies[dinnerMenuInCookies[dishType]]=dishType;
        }

        for(dishKey in dishesRetrievedBasedOnCookies) {
            var dish = dishesRetrievedBasedOnCookies[dishKey];
            console.log(dish.id);
            console.log(inverteddinnerMenuInCookies[dish.id]);
            that.addDishToMenu(dish, inverteddinnerMenuInCookies[dish.id]);

        }
       // that.broadcastItem();
      // $scope.selectedMenu = that.getFullMenu();
        //use dishesRetrievedBasedOnCookies and add it to dishmenu
        console.log(inverteddinnerMenuInCookies);
    }

    this.broadcastItem = function () {
        $rootScope.$broadcast('menuUpdated');
    }
    return this;

});

