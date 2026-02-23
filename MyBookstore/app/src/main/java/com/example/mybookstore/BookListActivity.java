package com.example.mybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Locale;

import java.util.ArrayList;
import java.util.HashMap;

public class BookListActivity extends AppCompatActivity {
    private ArrayList<String> books;
    private HashMap<String, BookInfo> bookDetails;
    private double totalPrice = 0.0;
    private TextView totalPriceText;
    private ArrayAdapter<String> adapter;
    //private ListView cartListView;
    private static final int DETAIL_REQUEST_CODE = 1; // Request code for DetailActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
       // cartListView = findViewById(R.id.cart_list_view);


        // Get selected category from intent
        String category = getIntent().getStringExtra("CATEGORY");
        books = new ArrayList<>();
        bookDetails = new HashMap<>();

        // Populate books dynamically based on category
        loadBooks(category);

        ListView listView = findViewById(R.id.book_list_view);
        totalPriceText = findViewById(R.id.total_price);
        Button seeCartButton = findViewById(R.id.see_cart);

        // Set up the ArrayAdapter to display book titles
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, books);
        listView.setAdapter(adapter);  // Link adapter with ListView

        // Handle item clicks on the ListView
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            String bookTitle = books.get(position);  // Get the clicked book's title
            openDetailActivity(bookTitle);  // Open the DetailActivity with the selected book's details
        });


        // Handle "See Cart" button click
        seeCartButton.setOnClickListener(v -> {
            // CartActivity will access CartManager directly
            startActivity(new Intent(this, CartActivity.class));
        });
    }

    private void updateCartView() {
        List<CartManager.CartItem> cartItems = CartManager.getInstance().getCartItems();
        List<String> displayItems = new ArrayList<>();
        for (CartManager.CartItem item : cartItems) {
            displayItems.add(item.getQuantity() + "x " + item.getTitle() + " - $" + String.format(Locale.US, "%.2f", item.getPrice() * item.getQuantity()));
        }


        // Update ListView
       // adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayItems);
       // cartListView.setAdapter(adapter);

        // Update total price
        double totalPrice = CartManager.getInstance().getTotalPrice();
        totalPriceText.setText(String.format(Locale.US, "Total: $%.2f", totalPrice));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartView();
    }

    /**
     * Open DetailActivity and send book details
     */
    private void openDetailActivity(String bookTitle) {
        BookInfo bookInfo = bookDetails.get(bookTitle);  // Get the BookInfo for the selected book
        if (bookInfo != null) {
            Intent intent = new Intent(this, DetailActivity.class);  // Create an Intent for DetailActivity
            intent.putExtra("BOOK_TITLE", bookInfo.title);
            intent.putExtra("BOOK_AUTHOR", bookInfo.author);
            intent.putExtra("BOOK_DESCRIPTION", bookInfo.description);
            intent.putExtra("BOOK_PRICE", bookInfo.price);
            intent.putExtra("BOOK_IMAGE", bookInfo.imageResId);
            startActivityForResult(intent, DETAIL_REQUEST_CODE);  // Start the activity and wait for results if necessary
        }
    }




    /**
     * Capture result from DetailActivity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DETAIL_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            double itemTotal = data.getDoubleExtra("BOOK_PRICE", 0.0) * data.getIntExtra("BOOK_QUANTITY", 1);
            String bookTitle = data.getStringExtra("BOOK_TITLE");
            String author = data.getStringExtra("BOOK_AUTHOR");
            int quantity = data.getIntExtra("BOOK_QUANTITY", 1);
            int imageResId = data.getIntExtra("BOOK_IMAGE", 0);

            // Add item to the cart
            CartManager.getInstance().addItem(bookTitle, author, itemTotal / quantity, quantity, imageResId);  // itemTotal / quantity to get unit price

            // Update the total price in BookListActivity
            totalPrice += itemTotal;
            totalPriceText.setText(String.format(Locale.US, "Total: $%.2f", totalPrice));
        }
    }





    /**
     * Load books based on category
     */
    private void loadBooks(String category) {
        if (category.equals("Cookbooks")) {
            addBook("Minecraft: Gather, Cook, Eat! Official Cookbook", "Tara Theoharis",
                    "Discover 40+ recipes like Mooshroom Burgers, Suspicious Stew, and The Cake! Featuring recipes that are ideal for every skill level (and player type), this cookbook is just what you need to bring a touch of Minecraft into your kitchen. So, what are you waiting for? It’s time to gather, cook, and eat!\n" +
                            "\n" +
                            "Building can be hungry work. And sometimes, you just need to take a break and enjoy the fruits of your labor, whether that’s a quick Baked Potato Bite while you’re hunkered down, waiting for a creeper to quit skulking at your door, or creating a celebratory feast for all your friends with a Buried Treasure Pie! Minecraft: The Official Cookbook gives you everything you need to build awesome meals, no matter your skill level.\n" +
                            "\n" +
                            "USER-FRIENDLY CONTENT FOR ALL SKILL LEVELS: With step-by-step directions and beautiful photos, learn to make iconic in-game dishes, in addition to an abundance of recipes inspired by the limitless world of Minecraft.\n" +
                            "\n" +
                            "PERFECT FOR EVERY HOME COOK (AND PLAYER TYPE!): Featuring a variety of vegetarian, vegan, and gluten-free recipes, this cookbook has something for everyone!\n" +
                            "\n" +
                            "BRING THE GAME TO LIFE: Build fun-focused dishes inspired by iconic biomes, fan-favorite creatures, and the items that shape this colorful world!\n" +
                            "\n" +
                            "40+ RECIPES FOR EVERY OCCASION: From quick snacks you can enjoy during a Minecraft play session, to decadent desserts and meals fit for the pickiest adventurer, this book contains recipes for both simple and celebratory fare.",
                    12.99, R.drawable.minecraft_cookbook);

            addBook("The Ultimate Final Fantasy XIV Cookbook: The Essential Culinarian Guide to Hydaelyn", "Victoria Rosenthal",
                    "Travel through the exciting culinary world of FINAL FANTASY XIV.\n" +
                            "\n" +
                            "Journey through the rich culinary landscape of FINAL FANTASY XIV. Featuring favorite flavors from across Hydaelyn and Norvrandt and easy-to-follow instructions, this tome provides numerous tips on how to make the most of your ingredients. Start your day with Farmer’s Breakfast, a very famous and simple-yet-delightful dish; savor the Knight’s Bread of Coerthas; dive into La Noscea’s Rolanberry Cheesecake, and many more.\n" +
                            "\n" +
                            "·      Exclusive Foreword written by game director, Naoki Yoshida.\n" +
                            "\n" +
                            "·      Perfect for cooks of every skill level. With step-by-step directions and beautiful photos, learn to make iconic in-game foods, bringing the lush culinary landscape of FINAL FANTASY XIV to life.\n" +
                            "\n" +
                            "·      Over 70 Recipes for every occasion. From quick snacks you can enjoy while exploring Eorzea to decadent desserts and meals fit for royalty, this book contains recipes for both simple and celebratory fare.\n" +
                            "\n" +
                            "·      Inspiring Photography. Gorgeous photos of finished recipes help ensure success!",
                    17.99,
                    R.drawable.final_fantasy_cookbook);

            addBook("The Unofficial Genshin Impact Cookbook: Boost Attacks, Increase Defense, and Restore Your Health with 60 Adventurous Recipes Inspired by the Fan-Favorite Video Game", "Kierra Sonderkerer, Nevyana Dimitrova",
                    "Immerse yourself in the world of the award-winning video game Genshin Impact as you learn to cook 60 unofficial yet totally delicious recipes from the hills of Mondstadt to the deserts of Sumeru.\n" +
                            "\n" +
                            "Named the Best Mobile Game of 2021 by The Game Awards, Genshin Impact has taken the gaming world by storm since its release in 2021. As players follow along with the adventures of a determined Traveler seeking their missing sibling, we’ve been introduced to new friends, exciting quests, epic boss battles, beautiful lands, and—best of all—tasty food!\n" +
                            "\n" +
                            "Now, you can bring some of your favorite attack-boosting and health-restoring foods from the game into the real world with The Unofficial Genshin Impact Cookbook. This cookbook features 60 delicious dishes inspired by the recipes you have collected from all over Teyvat, including:\n" +
                            "Crispy Potato-Shrimp Balls\n" +
                            "Omurice\n" +
                            "Afternoon Tea Pancakes\n" +
                            "Pearl and Jade Soup\n" +
                            "Honey-Glazed Roast\n" +
                            "Refreshing Jellied Mint\n" +
                            "And more!\n" +
                            "\n" +
                            "\n" +
                            "The Unofficial Genshin Impact Cookbook is perfect for Travelers of all kinds, whether you’re an avid gamer, cooking connoisseur, or simply another Genshin superfan!",
                    18.84, R.drawable.genshin_cookbook);

            addBook("The Unofficial Sims Cookbook: From Baked Alaska to Silly Gummy Bear Pancakes, 85+ Recipes to Satisfy the Hunger Need", "Taylor O’Halloran",
                    "Learn how to make all the meals you’ve seen your Sims devour with The Unofficial Sims Cookbook.\n" +
                            "\n" +
                            "Sure, you’ve honed your Sims’ cooking skills, but how are your skills IRL? Now, you can perfect the baked Alaska and lobster thermidor you’ve been watching your Sims make with The Unofficial Sims Cookbook.\n" +
                            "\n" +
                            "Learn the steps behind the classic simulated recipes:\n" +
                            "-Chili Con Carne\n" +
                            "-Silly Gummy Bear Pancakes\n" +
                            "-Grandma’s Comfort Soup\n" +
                            "-Minty Mocha Cupcakes\n" +
                            "-And more!\n" +
                            "\n" +
                            "Your hunger will be satisfied, and you may even start your career path towards becoming a famous chef! Dive straight in with the delicious recipes in The Unofficial Sims Cookbook.",
                    19.99, R.drawable.the_sims_cookbook);
        }
        else if (category.equals("Humour")) {
            addBook("Are You Gonna Eat That?: The Essential Collection of They Can Talk Comics", "Jimmy Craig",
                    "Do you know what your dog, cat, and neighborhood squirrels are saying behind your back? The truth comes out in this fully updated collection of comics from the viral hit THEY CAN TALK.\n" +
                            "\n" +
                            "Jimmy Craig, humor writer and artist behind the popular webcomic series \"They Can Talk,\" offers more than 100+ hilarious animal comics in this comprehensive collection imagining what it would be like if we had VIP access to the lives of our animal friends.\n" +
                            "\n" +
                            "These colorful illustrated comics include the inner thoughts of creatures from across the animal kingdom—from misunderstood sharks and trouble-making bears to the often complicated relationship between you and your pet cat. Get dating advice from raccoons, some life perspective from dogs, and learn why cats are always knocking things off of shelves.\n" +
                            "\n" +
                            "Updated to include brand new comics, fan favorites, redrawn classics, Are You Gonna Eat That? is the perfect quirky gift for any lover of animals, or for anyone who just loves to laugh.",
                    14.49, R.drawable.are_you_gonna_eat_that);

            addBook("Grumpy Cat: A Grumpy Book (unique Books, Humor Books, Funny Books For Cat Lovers)", "Grumpy Cat",
                    "Disgruntled tips and activities designed to put a frown on your face\n" +
                            "\n" +
                            "Featuring the sour expression of internet sensation Grumpy Cat: Internet sensation Grumpy Cat's epic feline frown has inspired legions of devoted fans. Celebrating the grouch in everyone, Grumpy Cat: A Grumpy Book teaches the fine art of grumpiness and includes enough bad attitude to cast a dark cloud over the whole world.\n" +
                            "\n" +
                            "• Featuring brand new as well as classic photos, grump-inspiring activities, and games\n" +
                            "• Delivers unmatched, hilarious grumpiness that puts any bad mood in perspective\n" +
                            "• The original Grumpy Cat was a small cat with a big frown who inspired a hugely popular meme that made even the grumpiest people around the world smile\n" +
                            "\n" +
                            "Turn that smile upside down with the help of Grumpy Cat.\n" +
                            "\n" +
                            "Celebrating the grouch in all of us.\n" +
                            "\n" +
                            "• A perfect coffee table, bathroom, or bar top conversation-starting book",
                    19.95, R.drawable.grumpy_cat);

            addBook("I Am Pusheen the Cat", "Claire Belton",
                    "Who is Pusheen? This collection of oh-so-cute kitty comics—featuring the chubby, tubby tabby who has taken the Internet by storm—will fill you in on all the basics.\n" +
                            "\n" +
                            "Things you should know about Pusheen.\n" +
                            "Birthday: February 18\n" +
                            "Sex: Female\n" +
                            "Where she lives: In the house, on the couch, underfoot\n" +
                            "Her favorite pastime: Blogging, sleeping\n" +
                            "Her best feature: Her toe beans\n" +
                            "Her favorite food: All of them\n" +
                            "\n" +
                            "Pusheen is a pleasantly plump cat who has warmed hearts and tickled funny bones of millions worldwide with her signature GIF animated bops, bounces, and tail wiggles. Now, Pusheen is ready to make the leap from digital to print in her first comic collection! Learn what makes her purr and find out why millions of people have already fallen in love with this naughty, adorable kitty. Featuring some of the most popular stories from Pusheen’s Tumblr and Facebook pages (plus a healthy serving of never-before-seen material), I Am Pusheen the Cat is a treat for cat lovers and comics fans alike.",
                    11.99, R.drawable.i_am_pusheen_the_cat);

            addBook("The Field Guide To Dumb Birds Of The Whole Stupid World", "Matt Kracht",
                    "This must-have sequel to the bestselling parody book The Field Guide to Dumb Birds of North America proves that all birds are fascinating, wonderful, idiotic jerks—no matter where in the world they reside.\n" +
                            "\n" +
                            "\"Full of NSFW names and descriptions for different species that'll have you LOL-ing even if you never actually go looking for said birds IRL.\"—Buzzfeed\n" +
                            "\n" +
                            "Following in the tracks of the first uproarious and beloved bird book in the series, this hilarious sequel ventures beyond to identify the stupidest birds around the world. Featuring birds from North and South America, Africa, Asia, Europe, and Oceania, author Matt Kracht identifies the dumb birds that manage to live all over the freaking place with snarky yet accurate names and humorous, anger-filled drawings. Offering a balance of fact and wit, this uproarious profanity-laden handbook will appeal to hardcore birders and casual bird lovers (and haters) alike.\n" +
                            "\n" +
                            "ENTERTAINING AND EDUCATIONAL: This laugh-out-loud funny spoof guide to all things wings includes a matching game, a bird descriptor checklist, and tips on how to identify a bird (you can tell a lot by looking into a bird's eyes, for example). Plus, each entry is accompanied by facts about a bird's (annoying) call, its (dumb) migratory pattern, its (downright tacky) markings, and more.\n" +
                            "\n" +
                            "POPULAR AUTHOR: Matt Kracht is an amateur birder, writer, and illustrator who enjoys creating books that celebrate the humor inherent in life's absurdities. Based in Seattle, he enjoys gazing out the window at the beautiful waters of Puget Sound and making fun of birds. Other amusing titles from Matt include The Field Guide to Dumb Birds of North America and OMFG, BEES! \n" +
                            "Perfect for:\n" +
                            "Birdwatching and nature enthusiasts\n" +
                            "People who think birds are creepy or annoying (and people who love birds but also enjoy a good laugh)\n" +
                            "Anyone looking for the ultimate coffee table book or bar-top conversation starter",
                    1.99, R.drawable.the_field_guide_to_dumb_birds_of_the_whole_stupid_world);
        }
        else if (category.equals("Japanese Fiction")) {
            addBook("Before The Coffee Gets Cold", "Toshikazu Kawaguchi",
                    "*NOW AN LA TIMES BESTSELLER*\n" +
                            "\n" +
                            "*AN INTERNATIONAL BESTSELLER*\n" +
                            "\n" +
                            "The first book in the five million-copy bestselling magical realism series\n" +
                            "\n" +
                            "If you could go back in time, who would you want to meet?\n" +
                            "\n" +
                            "In a small back alley of Tokyo, there is a café that has been serving carefully brewed coffee for more than one hundred years. Local legend says that this shop offers something else besides coffee—the chance to travel back in time.\n" +
                            "\n" +
                            "Over the course of one summer, four customers visit the café in the hopes of making that journey. But time travel isn’t so simple, and there are rules that must be followed. Most important, the trip can last only as long as it takes for the coffee to get cold. \n" +
                            "\n" +
                            "Prepare to meet four visitors, each of whom is hoping to make use of the cafe’s time-travelling offer in order to:\n" +
                            "confront the man who left them\n" +
                            "receive a letter from their husband whose memory has been taken by Alzheimer's\n" +
                            "see their sister one last time, and\n" +
                            "meet the daughter they never got the chance to know.\n" +
                            "\n" +
                            "Heartwarming, wistful, mysterious and delightfully quirky, Toshikazu Kawaguchi’s internationally bestselling novel explores the age-old question: What would you change if you could travel back in time?",
                    3.99, R.drawable.before_the_coffee_gets_cold);

            addBook("The Full Moon Coffee Shop", "Mai Mochizuki",
                    "NATIONAL BESTSELLER • Translated from the Japanese bestseller, a charming and magical novel that reminds us it’s never too late to follow our stars.\n" +
                            "\n" +
                            "“Mochizuki dazzles in her beautifully crafted contemporary fantasy debut. . . . This gentle fantasy is not to be missed.”—Publishers Weekly, starred review (Best Books of the Year)\n" +
                            "\n" +
                            "In Japan, cats are a symbol of good luck. As the myth goes, if you are kind to them, they’ll one day return the favor. And if you are kind to the right cat, you might just find yourself invited to a mysterious coffee shop under a glittering Kyoto moon.\n" +
                            "\n" +
                            "This particular coffee shop is like no other. It has no fixed location, no fixed hours, and it seemingly appears at random.\n" +
                            "\n" +
                            "It’s also run by talking cats.\n" +
                            "\n" +
                            "While customers at the Full Moon Coffee Shop partake in cakes and coffees and teas, the cats also consult their star charts, offering cryptic wisdom, and letting them know where their lives veered off course.\n" +
                            "\n" +
                            "Every person who visits the shop has been feeling more than a little lost. For a down-on-her-luck screenwriter, a romantically stuck movie director, a hopeful hairstylist, and a technologically challenged website designer, the coffee shop’s feline guides will set them back on their fated paths. For there is a very special reason the shop appeared to each of them . . .",
                    14.99, R.drawable.the_full_moon_coffee_shop);

            addBook("The Kamogawa Food Detectives", "Hisashi Kashiwai",
                    "The Kamogawa Food Detectives is the first book in the bestselling, mouth-watering Japanese series, for fans of Before the Coffee Gets Cold.\n" +
                            "\n" +
                            "What’s the one dish you’d do anything to taste just one more time?\n" +
                            "\n" +
                            "\n" +
                            "Down a quiet backstreet in Kyoto exists a very special restaurant. Run by Koishi Kamogawa and her father Nagare, the Kamogawa Diner serves up deliciously extravagant meals. But that's not the main reason customers stop by . . .\n" +
                            "\n" +
                            "\n" +
                            "The father-daughter duo are 'food detectives'. Through ingenious investigations, they are able to recreate dishes from a person’s treasured memories – dishes that may well hold the keys to their forgotten past and future happiness. The restaurant of lost recipes provides a link to vanished moments, creating a present full of possibility.\n" +
                            "\n" +
                            "\n" +
                            "A bestseller in Japan, The Kamogawa Food Detectives is a celebration of good company and the power of a delicious meal.",
                    16.99, R.drawable.the_kamogawa_food_detectives);

            addBook("We'll Prescribe You a Cat", "Syou Ishida",
                    "A USA Today Bestseller\n" +
                            "\n" +
                            "A cat a day keeps the doctor away…\n" +
                            "\n" +
                            "Discover the award-winning, bestselling Japanese novel that has become an international sensation in this utterly charming, vibrant celebration of the healing power of cats.\n" +
                            "\n" +
                            "Tucked away in an old building at the end of a narrow alley in Kyoto, the Kokoro Clinic for the Soul can only be found by people who are struggling in their lives and genuinely need help. The mysterious clinic offers a unique treatment to those who find their way there: it prescribes cats as medication. Patients are often puzzled by this unconventional prescription, but when they “take” their cat for the recommended duration, they witness profound transformations in their lives, guided by the playful, empathetic, occasionally challenging yet endearing cats.\n" +
                            "\n" +
                            "Throughout the pages, the power of the human-animal bond is revealed as a disheartened businessman finds unexpected joy in physical labor, a young girl navigates the complexities of elementary school cliques, a middle-aged man struggles to stay relevant at work and home, a hardened bag designer seeks emotional balance, and a geisha finds herself unable to move on from the memory of her lost cat. As the clinic’s patients navigate their inner turmoil and seek resolution, their feline companions lead them toward healing, self-discovery, and newfound hope.",
                    16.99, R.drawable.well_prescribe_you_a_cat);
        }
        else if (category.equals("Manga")) {
            addBook("Delicious In Dungeon, Vol. 1", "Ryoko Kui",
                    "When young adventurer Laios and his company are attacked and soundly thrashed by a dragon deep in a dungeon, the party loses all its money and provisions...and a member! They're eager to go back and save her, but there is just one problem: If they set out with no food or coin to speak of, they're sure to starve on the way! But Laios comes up with a brilliant idea: \"Let's eat the monsters!\" Slimes, basilisks, and even dragons...none are safe from the appetites of these dungeon-crawling gourmands!",
                    9.99, R.drawable.delicious_in_dungeon_vol_1);

            addBook("Frieren: Beyond Journey's End, Vol. 1", "Kanehito Yamada",
                    "The adventure is over but life goes on for an elf mage just beginning to learn what living is all about.\n" +
                            "\n" +
                            "Elf mage Frieren and her courageous fellow adventurers have defeated the Demon King and brought peace to the land. But Frieren will long outlive the rest of her former party. How will she come to understand what life means to the people around her?\n" +
                            "\n" +
                            "Decades after their victory, the funeral of one her friends confronts Frieren with her own near immortality. Frieren sets out to fulfill the last wishes of her comrades and finds herself beginning a new adventure…",
                    7.99, R.drawable.frieren_beyond_journeys_end_vol_1);

            addBook("Solo Leveling, Vol. 1", "Chugong",
                    "Known as the Weakest Hunter of All Mankind, E-rank hunter Jinwoo Sung’s contribution to raids amounts to trying not to get killed. Unfortunately, between his mother’s hospital bills, his sister’s tuition, and his own lack of job prospects, he has no choice but to continue to put his life on the line. So when an opportunity arises for a bigger payout, he takes it…only to come face-to-face with a being whose power outranks anything he’s ever seen! With the party leader missing an arm and the only healer a quivering mess, can Jinwoo some\u00ADhow find them a way out?",
                    13.99, R.drawable.solo_leveling_vol_1);

            addBook("Spy X Family, Vol. 1", "Tatsuya Endo",
                    "An action-packed comedy about a fake family that includes a spy, an assassin and a telepath!\n" +
                            "\n" +
                            "Master spy Twilight is unparalleled when it comes to going undercover on dangerous missions for the betterment of the world. But when he receives the ultimate assignment—to get married and have a kid—he may finally be in over his head!\n" +
                            "\n" +
                            "Not one to depend on others, Twilight has his work cut out for him procuring both a wife and a child for his mission to infiltrate an elite private school. What he doesn’t know is that the wife he’s chosen is an assassin and the child he’s adopted is a telepath!",
                    7.99, R.drawable.spy_x_family_vol_1);
        }
    }

    /**
     * Adds a book to the category list
     */
    private void addBook(String title, String author, String description, double price, int imageResId) {
        books.add(title);
        bookDetails.put(title, new BookInfo(title, author, description, price, imageResId));
    }

    /**
     * Helper class to store book information
     */
    private static class BookInfo {
        String title, author, description;
        double price;
        int imageResId;

        BookInfo(String title, String author, String description, double price, int imageResId) {
            this.title = title;
            this.author = author;
            this.description = description;
            this.price = price;
            this.imageResId = imageResId;
        }
    }
}
