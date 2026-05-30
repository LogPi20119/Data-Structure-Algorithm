export const products = [
  // ---- Women ----
  { id: 1, image: 'https://i.pinimg.com/originals/95/11/d7/9511d7bff723873ea1108c579ac952cc.jpg', name: "Basic T-Shirt", price: 300, category: "tops", gender: "women", sale: false },
  { id: 2, image: 'https://api.etonshirts.com/v1/retail/image/1620/bynder/fb881be9-8cb8-461c-8133-c89ed3da2908/dark-brown-linen_2023-12-13T160056882Z.webp', name: "Linen Shirt", price: 450, category: "tops", gender: "women", sale: true, salePercent: 20 },
  { id: 3, image: 'https://cdn-images.farfetch-contents.com/19/46/77/97/19467797_43332867_1000.jpg', name: "Denim Jacket", price: 650, category: "tops", gender: "women", sale: false },
  { id: 4, image: 'https://www.flannels.com/images/imgzoom/51/51435618_xxl.jpg', name: "Slim Trousers", price: 500, category: "bottoms", gender: "women", sale: true, salePercent: 15 },
  { id: 5, image: 'https://tse2.mm.bing.net/th/id/OIP.NXQbaxlnxocJWkT_AvKPiQHaJQ?r=0&rs=1&pid=ImgDetMain&o=7&rm=3', name: "Midi Skirt", price: 420, category: "bottoms", gender: "women", sale: false },
  { id: 6, image: 'https://i.pinimg.com/736x/f1/72/03/f17203bc808e630091c3610d866483b9.jpg', name: "High Heels", price: 750, category: "shoes", gender: "women", sale: true, salePercent: 30 },
  { id: 7, image: 'https://i5.walmartimages.com/seo/Ataiwee-Women-s-Flat-Slide-Sandals-Ladies-Casual-Criss-Cross-Slip-On-Walk-Summer-Dress-Slide-Sandals_0978512d-d2d3-4581-8d56-d53bb7f046e3.c710d46568308ef00deb077d3fcddb95.jpeg', name: "Flat Sandals", price: 280, category: "shoes", gender: "women", sale: false },
  { id: 8, image: 'https://cedarandmyrrh.com/cdn/shop/files/canvas-tote-bag.jpg?v=1721251838&width=2000', name: "Canvas Tote Bag", price: 280, category: "accessories", gender: "women", sale: true, salePercent: 10 },
  // ---- Men ----
  { id: 9, image: 'https://m.media-amazon.com/images/I/81NahZXF9QL._AC_SL1396_.jpg', name: "Classic Polo", price: 380, category: "tops", gender: "men", sale: false },
  { id: 10, image: 'https://www.mrporter.com/variants/images/1647597308028567/in/w2000_q60.jpg', name: "Bomber Jacket", price: 720, category: "tops", gender: "men", sale: true, salePercent: 25 },
  { id: 11, image: 'https://cdn-img.prettylittlething.com/6/4/f/f/64ff82eb824966ae425910828e101a9e00766e30_CNL7126_5_grey_high_waist_straight_leg_jeans.jpg?imwidth=2048', name: "Straight Jeans", price: 480, category: "bottoms", gender: "men", sale: false },
  { id: 12, image: 'https://i.pinimg.com/originals/ec/75/83/ec75831eae9a6f983cb57e571d81882b.jpg', name: "Khaki Shorts", price: 320, category: "bottoms", gender: "men", sale: true, salePercent: 20 },
  { id: 13, image: 'https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/4ff2fe4a-4f74-4189-82f0-3ea780f9389d/air-max-excee-womens-shoes-jKsgMj.png', name: "Sneakers", price: 800, category: "shoes", gender: "men", sale: false },
  { id: 14, image: 'https://cdn.media.amplience.net/i/clarks/26179541_GW_1', name: "Leather Loafers", price: 950, category: "shoes", gender: "men", sale: true, salePercent: 15 },
  { id: 15, image: 'https://leatherdirect.co.nz/wp-content/uploads/2025/04/Parisian_YPEL_Pelham_Belt_Green_300.jpg', name: "Leather Belt", price: 220, category: "accessories", gender: "men", sale: false },
  { id: 16, image: 'https://eu-images.contentstack.com/v3/assets/blt7dcd2cfbc90d45de/blt32044d8c5742c9e2/648187cea4ecd7633ad014a7/28705.jpg?format=pjpg&auto=webp&quality=75%2C90&width=1200', name: "Bucket Hat", price: 180, category: "accessories", gender: "men", sale: true, salePercent: 30 },
  // ---- Unisex ----
  { id: 17, image: "https://tse2.mm.bing.net/th/id/OIP.MhTl-7qbCgokAgOZGxYIqwHaLH?r=0&rs=1&pid=ImgDetMain&o=7&rm=3", name: "Oversized Hoodie", price: 540, category: "tops", gender: "unisex", sale: true, salePercent: 10 },
  { id: 18, image: "https://images.puma.com/image/upload/f_auto,q_auto,b_rgb:fafafa,w_600,h_600/global/629744/01/fnd/PNA/fmt/png/PUMA-Wardrobe-Essentials-Women's-Oversized-Tee", name: "Oversized Essential Tee", price: 39, gender: "unisex", category: "tops", sale: false },
  { id: 19, image: "https://tse4.mm.bing.net/th/id/OIP.Ln_seNIH0_EHBLGK41Y0NwHaHa?r=0&rs=1&pid=ImgDetMain&o=7&rm=3", name: "Minimal Wide Pants", price: 65, gender: "unisex", category: "bottoms", sale: false },
];

export const productMap = new Map();

export function initData() {
  products.forEach(p => productMap.set(p.id, p));
}
