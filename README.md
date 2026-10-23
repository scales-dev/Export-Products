# Exporting Products
We want to switch from one.com to EKM (and eventually to our own).
For this we need our products off.
one.com doesn't natively offer a way to export their data...
However, they must request the products for the site somewhere.
Their API (ew dynamically generating HTML) can be found by doing the following:
- Open a one.com site, and press F12 ![img.png](repo/f12%20site.png)
- Open the "network" tab, and refresh the page with CTRL+R or F5 ![img.png](repo/open%20network.png)
- CTRL+F "api" and look for one like so ![img.png](repo/search%20api.png)
- Steal the API URL, in my case https://webshop.one.com/api/v2/cluster/myexoticfruit.com/domain/myexoticfruit.com/product?&sortBy=MANUAL&sort=ASC&pageOffset=0&pageSize=70&filters=%7B%7D
- Copy the request headers and params

Requesting this URL returns a HUGE list of products, each looking like this (surrounded by data: { result: [ ] }):

```json
{
  "id": "4d9e4edb-8f74-490c-8e46-3c1f807f3ab7",
  "shopId": "5cae0f86-c728-46e3-ae9e-9db60c50bac9",
  "name": "Turmeric",
  "description": "<p><span style=\"\">Looking for a way to add a kick of spice to your mealtimes? Our fresh turmeric is just the thing for you!</span></p><p><span style=\"\">Fresh turmeric is elongated and slender in shape, somewhat resembling a long finger, and has brownish-orange skin that is thin, rough, and marked with wrinkles and knobs. Beneath this textured skin is eye-catching bright orange flesh that will wow your tastebuds with its delicious flavour: it has distinctive warming properties and wonderful peppery notes, with strong undertones of carrot and ginger. Did you know it is one of the ingredients that constitutes curry powder?</span></p><p><span style=\"\">Although most often boiled and dried to create turmeric powder, fresh turmeric can also be fried and pickled, and is perfect for adding gentle freshness to curries. Its colour alone is enough to make turmeric a superior culinary ingredient - it is sure to brighten up any dish! Indeed, it is also known as 'Indian saffron' because it is often used in Indian cuisine as a substitute for saffron in bringing some visual vibrancy. Check out the 'Further information' tab below for some of our favourite recipes, including an extremely healthy tropical turmeric smoothie!</span></p><p><span style=\"\">As well as being versatile and delectable, turmeric also boasts many nutritional benefits. In particular, its primary active ingredient, curcumin, has powerful anti-inflammatory and antioxidant properties that have made the spice popular in Chinese and Ayurvedic medicines for centuries.&nbsp;</span></p><p><span style=\"\">We also have </span><b><strong>organic</strong></b><span style=\"\"> and </span><b><strong>fair trade</strong></b><span style=\"\"> turmeric from small-scale farmers deep in Peru.</span></p>",
  "label": "",
  "ribbonId": null,
  "discountPromotion": {
    "ribbon": null,
    "description": null
  },
  "images": [
    {
      "id": "bd3e2999-f608-4be7-a54e-da030d4b2ed6",
      "url": "https://myexoticfruit.com/webshopmedia/Turmeric-1589461369730.jpg",
      "orderIndex": 0,
      "shopId": "5cae0f86-c728-46e3-ae9e-9db60c50bac9",
      "productId": "4d9e4edb-8f74-490c-8e46-3c1f807f3ab7",
      "improImageUrl": "https://impro.usercontent.one/appid/oneComShop/domain/myexoticfruit.com/media/myexoticfruit.com/webshopmedia/Turmeric-1589461369730.jpg?&withoutEnlargement&resize=960+9999&webp&quality=85",
      "isThumbnail": false
    }
  ],
  "status": "visible",
  "categories": [
    {
      "id": "b9f520cc-f78f-49e3-8be8-956e961061b9",
      "name": "Ingredients & aromatics",
      "url": "ingredients-and-aromatics",
      "isSample": false,
      "meta": "Explore our selection of high-quality ingredients & aromatics to elevate your cooking. Discover unique flavors and aromas today!",
      "pageTitle": "Premium Ingredients & Aromatics for All Your Culinary Needs",
      "isHidden": false,
      "sortOrder": 5,
      "sortProducts": "MANUAL",
      "count": 0,
      "createTime": "2023-04-24T21:38:21.736982",
      "modified": "2026-07-28T15:20:03.316104",
      "deleteTime": null,
      "shopId": "5cae0f86-c728-46e3-ae9e-9db60c50bac9",
      "channels": []
    }
  ],
  "optionTypes": [
    {
      "name": "Choose your quantity;",
      "values": [
        "200g",
        "1kg",
        "2kg",
        "200g ORGANIC",
        "1kg ORGANIC"
      ]
    }
  ],
  "url": "turmeric",
  "meta": "Discover vibrant turmeric with delicious carrot and ginger notes. Perfect for adding flavour and colour to your dishes. Order now for UK delivery.",
  "pageTitle": "Turmeric",
  "modifiedTime": "2026-10-15T12:02:34.984Z",
  "dbNameSortInAscIndex": "480",
  "archived": false,
  "variants": [
    {
      "id": "3a215b85-5bb8-486f-93ed-be6d3e3ee0e1",
      "productId": "4d9e4edb-8f74-490c-8e46-3c1f807f3ab7",
      "optionsValues": {
        "Choose your quantity;": "2kg"
      },
      "quantity": 0,
      "unlimited": true,
      "weight": 2.2,
      "sku": null,
      "hash": "376170",
      "shopId": "5cae0f86-c728-46e3-ae9e-9db60c50bac9",
      "imageId": "bd3e2999-f608-4be7-a54e-da030d4b2ed6",
      "price": 37.4,
      "oldPrice": null,
      "discountPromotion": null
    },
    {
      "id": "68b5b60c-6778-4fa1-a849-fe93703a7fb7",
      "productId": "4d9e4edb-8f74-490c-8e46-3c1f807f3ab7",
      "optionsValues": {
        "Choose your quantity;": "1kg"
      },
      "quantity": 0,
      "unlimited": true,
      "weight": 1.1,
      "sku": null,
      "hash": "786073",
      "shopId": "5cae0f86-c728-46e3-ae9e-9db60c50bac9",
      "imageId": "bd3e2999-f608-4be7-a54e-da030d4b2ed6",
      "price": 20.35,
      "oldPrice": null,
      "discountPromotion": null
    },
    {
      "id": "636dae5c-88c3-47b3-a19c-65404a3d33ec",
      "productId": "4d9e4edb-8f74-490c-8e46-3c1f807f3ab7",
      "optionsValues": {
        "Choose your quantity;": "200g"
      },
      "quantity": 0,
      "unlimited": true,
      "weight": 0.22,
      "sku": null,
      "hash": "205492",
      "shopId": "5cae0f86-c728-46e3-ae9e-9db60c50bac9",
      "imageId": "bd3e2999-f608-4be7-a54e-da030d4b2ed6",
      "price": 4.95,
      "oldPrice": null,
      "discountPromotion": null
    },
    {
      "id": "77056b83-f83c-47d6-bc50-c6a5d6c0d50b",
      "productId": "4d9e4edb-8f74-490c-8e46-3c1f807f3ab7",
      "optionsValues": {
        "Choose your quantity;": "200g ORGANIC"
      },
      "quantity": 0,
      "unlimited": true,
      "weight": 2.2,
      "sku": null,
      "hash": "295561",
      "shopId": "5cae0f86-c728-46e3-ae9e-9db60c50bac9",
      "imageId": "bd3e2999-f608-4be7-a54e-da030d4b2ed6",
      "price": 5.5,
      "oldPrice": null,
      "discountPromotion": null
    },
    {
      "id": "05ad9402-dd58-4122-9255-57f16b08c0b0",
      "productId": "4d9e4edb-8f74-490c-8e46-3c1f807f3ab7",
      "optionsValues": {
        "Choose your quantity;": "1kg ORGANIC"
      },
      "quantity": 0,
      "unlimited": true,
      "weight": 2.2,
      "sku": null,
      "hash": "880582",
      "shopId": "5cae0f86-c728-46e3-ae9e-9db60c50bac9",
      "imageId": "bd3e2999-f608-4be7-a54e-da030d4b2ed6",
      "price": 27.5,
      "oldPrice": null,
      "discountPromotion": null
    }
  ],
  "maxPerOrder": 0,
  "additionalInfo": [
    {
      "title": "Also known as",
      "description": "<p><em>N/A.</em></p>"
    },
    {
      "title": "Origin",
      "description": "<p><em>Uganda or India.</em></p>\n<p><em>Our Organic turmeric is from Peru.</em></p>"
    },
    {
      "title": "Preparation",
      "description": "<p><em>Wash thoroughly before use.&nbsp;</em></p>"
    },
    {
      "title": "Usage",
      "description": "<p><em>Turmeric is most often boiled and then turned into a powder, but it can also be pickled, fried, and much more!</em></p>"
    },
    {
      "title": "Storage",
      "description": "<p><em>Store in a cool, dry place.</em></p>"
    },
    {
      "title": "Average weight",
      "description": "<p>.</p>"
    },
    {
      "title": "Seasonality",
      "description": "<p><em>Turmeric is usually available all year round.</em></p>"
    },
    {
      "title": "Further information",
      "description": "<p>Wondering how best to enjoy your turmeric? We'd recommend using it to make <a title=\"Tropical turmeric smoothie - recipe\" href=\"https://savorylotus.com/creamy-tropical-turmeric-smoothie-dairy-free/\" target=\"_blank\" rel=\"noopener\">this</a> supremely healthy tropical turmeric smoothie; <a title=\"Turmeric-roasted cauliflower - recipe\" href=\"https://reluctantentertainer.com/skinnytastes-turmeric-roasted-cauliflower/\" target=\"_blank\" rel=\"noopener\">this</a> delicious turmeric-roasted cauliflower, and <a title=\"Turmeric ice cream - recipe\" href=\"http://cookbad.blogspot.com/2008/02/turmeric-ice-cream-or-semifreddo.html\" target=\"_blank\" rel=\"noopener\">this</a> wonderful turmeric ice cream!</p>"
    }
  ],
  "createdTime": "2020-05-14T13:05:50.940Z",
  "productOrder": 617,
  "deleteTime": null,
  "keywords": [
    "turmeric"
  ],
  "files": [],
  "type": "PHYSICAL",
  "giftCardExpiry": null,
  "isSample": false,
  "bulkUploadTimeStamp": null,
  "channels": [
    {
      "id": "5bba23d0-8e78-4111-bf9e-65d642803811",
      "name": "STOREFRONT",
      "default": true,
      "description": "Webshop offerred storefront"
    }
  ]
}
```

To get the related products we have to request a different API for EVERY PRODUCT.
But that doesn't take too long, overall this takes ~1 minute for me to request all relevant data on 491 products.