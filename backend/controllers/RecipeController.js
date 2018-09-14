const express = require('express');
const router = express.Router();
const bodyParser = require('body-parser');

router.use(bodyParser.urlencoded({extended: false}));
router.use(bodyParser.json());

const User = require('./../models/User');
const Recipe = require('./../models/Recipe');

const VerifyToken = require('./../auth/VerifyToken');
const multer = require('multer');

const storage = multer.diskStorage({
    destination: function(req, file, cb) {
        cb(null, 'uploads/');
    },
    filename: function(req, file, cb) {
        cb(null, new Date().toISOString().replace(/:/g, '-') + file.originalname);
    }
});

const upload = multer({
    storage: storage
});

//POST recipe
router.post('/', VerifyToken, upload.single('recipePhoto'), (req, res) => {

    console.log("req.body: ");
    console.log(req.body);
    console.log("req.file: ");
    console.log(req.file);
    

    Recipe.create({
        title: req.body.title,
        authorId: req.userId,
        createdAt: Date.now(),
        lastEditedAt: Date.now(),
        recipePhotoUrl: req.file.path,
        ingredients: req.body.ingredients.split(", "),
        youtubeLink: req.body.youtubeLink,
        directions: req.body.directions.split(", ")
    }, (err, recipe) => {
        if (err) 
            return res.status(500).send("Internal server error while posting a recipe");
        if (!recipe)
            return res.status(404).send("Posting a recipe: recipe not found");

        User.findById(recipe.authorId).then((user) => {
            user.posts.unshift(recipe._id);

            User.findByIdAndUpdate(recipe.authorId, {
                posts: user.posts
            }).then((user) => {
                res.status(200).send(recipe);
            })

            
        });

        
        
        

        
    });
});

// GET recipe by Id
router.get("/:id", async (req, res) => {
    console.log(req.body);

    let recipeId = req.params.id;
    let recipe = await Recipe.findById(recipeId);
    let author = await User.findById(recipe.authorId);



    res.status(200).send({
        id: recipeId,
        title: recipe.title,
        authorId: recipe.authorId,
        authorUsername: author.username,
        createdAt: recipe.createdAt,
        lastEditedAt: recipe.lastEditedAt,
        recipePhotoUrl: recipe.recipePhotoUrl,
        youtubeLink: recipe.youtubeLink,
        directions: recipe.directions,
        ingredients: recipe.ingredients,
        comments: recipe.comments,
        likes: recipe.likes
    });
});

router.patch('/:id', VerifyToken, async (req, res) => {
    let recipeId = req.params.id;
    
    let recipe = await Recipe.findById(recipeId);
    let likesArray = recipe.likes;


    console.log(req.headers['action']);
    if (req.headers["action"] === "add") {
        likesArray.unshift(req.userId);
    } else {
        let index = likesArray.indexOf(req.userId);
        likesArray.splice(index, 1);
    }
    

    Recipe.findByIdAndUpdate(recipeId, {
         likes: likesArray
    }, (err, recipe) => {
        if (!err && recipe) {
            res.status(200).send();
        }

    })
})

module.exports = router;