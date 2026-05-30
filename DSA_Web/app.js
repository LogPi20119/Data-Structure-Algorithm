// ===================== IMPORT =====================
import { products, initData, productMap } from "./data.js";
import { pushHistory } from "./algorithms.js";

// ===================== STATE =====================
let activeFilters = new Set();
let currentCategory = "all";
let cart = []; // array of cart items: { id, quantity }
let activeNameKeyword = "";
let activePriceRange = { min: 0, max: Infinity };
let defaultPriceRange = { min: 0, max: Infinity };

// ===================== DSA ALGORITHM: Shipping Calculation — O(1) =====================
function calculateShipping(subtotal) {
  if (subtotal >= 200) return 0;   // FREE
  if (subtotal >= 100) return 10;  // $10
  return 20;                        // $20
}

// ===================== DSA ALGORITHM: Discount Calculation — O(1) =====================
function calculateDiscount(subtotal) {
  if (subtotal >= 300) return 30;
  return 0;
}

function getProductPrice(product) {
  return product.sale
    ? Math.round(product.price * (1 - product.salePercent / 100))
    : product.price;
}

// DSA ALGORITHM: Reduce - O(n)
function getCartItemCount() {
  return cart.reduce((total, item) => total + item.quantity, 0);
}

function updateCartCount() {
  const count = getCartItemCount();
  document.getElementById("cart-count").innerText = count;
  document.getElementById("total").innerText = count;
}

function updatePriceRangeLabel() {
  const label = document.getElementById("price-range-label");
  if (!label) return;

  label.innerText = `$${activePriceRange.min} - $${activePriceRange.max}`;
}

function setupPriceRangeFilter() {
  const minInput = document.getElementById("price-min");
  const maxInput = document.getElementById("price-max");
  if (!minInput || !maxInput) return;

  const prices = products.map(product => getProductPrice(product));
  const minPrice = Math.min(...prices);
  const maxPrice = Math.max(...prices);

  defaultPriceRange = { min: minPrice, max: maxPrice };
  activePriceRange = { ...defaultPriceRange };

  [minInput, maxInput].forEach(input => {
    input.min = minPrice;
    input.max = maxPrice;
    input.step = 1;
  });

  minInput.value = minPrice;
  maxInput.value = maxPrice;
  updatePriceRangeLabel();
}

// ===================== FILTER BY PRICE RANGE =====================
// DSA ALGORITHM: Range filtering - O(n)
window.updatePriceRange = function (changedHandle) {
  const minInput = document.getElementById("price-min");
  const maxInput = document.getElementById("price-max");
  if (!minInput || !maxInput) return;

  let minValue = Number(minInput.value);
  let maxValue = Number(maxInput.value);

  if (minValue > maxValue) {
    if (changedHandle === "min") {
      maxValue = minValue;
      maxInput.value = maxValue;
    } else {
      minValue = maxValue;
      minInput.value = minValue;
    }
  }

  activePriceRange = { min: minValue, max: maxValue };
  updatePriceRangeLabel();
  renderProducts();
};

// ===================== HERO SALE SLIDER =====================
let heroSaleIndex = 0;
let heroSaleTimer = null;
const HERO_SALE_INTERVAL = 5000;

function getSaleProducts() {
  return products.filter(product => product.sale);
}

function updateHeroSaleSlider() {
  const track = document.getElementById("hero-sale-track");
  const dots = document.querySelectorAll(".hero-sale-dot");
  const saleProducts = getSaleProducts();

  if (!track || saleProducts.length === 0) return;

  heroSaleIndex = (heroSaleIndex + saleProducts.length) % saleProducts.length;
  track.style.transform = `translateX(-${heroSaleIndex * 100}%)`;

  dots.forEach((dot, index) => {
    dot.classList.toggle("active", index === heroSaleIndex);
    dot.setAttribute("aria-current", index === heroSaleIndex ? "true" : "false");
  });
}

function startHeroSaleAutoPlay() {
  clearInterval(heroSaleTimer);

  if (getSaleProducts().length <= 1) return;

  heroSaleTimer = setInterval(() => {
    heroSaleIndex += 1;
    updateHeroSaleSlider();
  }, HERO_SALE_INTERVAL);
}

function setupHeroSaleSwipe() {
  const slider = document.getElementById("hero-sale-slider");
  if (!slider || slider.dataset.swipeReady === "true") return;

  let startX = 0;

  slider.addEventListener("touchstart", event => {
    startX = event.touches[0].clientX;
    clearInterval(heroSaleTimer);
  }, { passive: true });

  slider.addEventListener("touchend", event => {
    const endX = event.changedTouches[0].clientX;
    const distance = endX - startX;

    if (Math.abs(distance) > 40) {
      window.slideHeroSale(distance < 0 ? 1 : -1);
    } else {
      startHeroSaleAutoPlay();
    }
  }, { passive: true });

  slider.dataset.swipeReady = "true";
}

function renderHeroSaleSlider() {
  const track = document.getElementById("hero-sale-track");
  const dots = document.getElementById("hero-sale-dots");
  if (!track || !dots) return;

  const saleProducts = getSaleProducts();
  if (saleProducts.length === 0) {
    track.innerHTML = "";
    dots.innerHTML = "";
    return;
  }

  track.innerHTML = saleProducts.map(product => {
    const finalPrice = getProductPrice(product);

    return `
      <article class="hero-sale-slide">
        <img src="${product.image}" alt="${product.name}">
        <button class="hero-tag" onclick="window.filterGender('sale')" type="button">Sale -${product.salePercent}%</button>
        <div class="hero-sale-info">
          <div class="hero-sale-category">${product.category.toUpperCase()}</div>
          <h2>${product.name}</h2>
          <div class="hero-sale-price">
            <span>$${finalPrice}</span>
            <del>$${product.price}</del>
          </div>
        </div>
      </article>
    `;
  }).join("");

  dots.innerHTML = saleProducts.map((product, index) => `
    <button class="hero-sale-dot" onclick="window.goHeroSaleSlide(${index})" type="button" aria-label="Show ${product.name}"></button>
  `).join("");

  setupHeroSaleSwipe();
  updateHeroSaleSlider();
  startHeroSaleAutoPlay();
}

window.slideHeroSale = function (direction) {
  heroSaleIndex += direction;
  updateHeroSaleSlider();
  startHeroSaleAutoPlay();
};

window.goHeroSaleSlide = function (index) {
  heroSaleIndex = index;
  updateHeroSaleSlider();
  startHeroSaleAutoPlay();
};

// ===================== RENDER CART =====================
// DSA: Uses Map lookup O(1) per item, total O(n)
function renderCart() {
  const container = document.getElementById("cart");
  if (!container) return;

  container.innerHTML = "";

  if (cart.length === 0) {
    container.innerHTML = "<p style='color:#999;font-size:14px;text-align:center;padding:32px 0;'>Your cart is empty</p>";
    return;
  }

  let subtotal = 0;

  // DSA ALGORITHM: Filtering — O(n) to build cart items
  cart.forEach(item => {
    const p = productMap.get(item.id);
    if (!p) return;

    const price = getProductPrice(p);
    const lineTotal = price * item.quantity;

    subtotal += lineTotal;

    container.innerHTML += `
      <div class="cart-item">
        <div class="cart-item-img">
          <img src="${p.image}" alt="${p.name}">
        </div>
        <div class="cart-item-info">
          <div class="cart-item-name">${p.name}</div>
          <div class="cart-item-meta">${p.category.toUpperCase()}</div>
          <div class="cart-item-price">$${price} each</div>
          <div class="cart-quantity-row">
            <button class="cart-qty-btn" onclick="window.changeCartQuantity(${item.id}, -1)" title="Decrease quantity">-</button>
            <span class="cart-qty-value">${item.quantity}</span>
            <button class="cart-qty-btn" onclick="window.changeCartQuantity(${item.id}, 1)" title="Increase quantity">+</button>
            <span class="cart-line-total">$${lineTotal}</span>
          </div>
        </div>
        <button class="cart-remove-btn" onclick="window.removeFromCart(${item.id})" title="Remove">×</button>
      </div>
    `;
  });

  // DSA ALGORITHM: Shipping Calculation — O(1)
  const shipping = calculateShipping(subtotal);
  // DSA ALGORITHM: Discount Calculation — O(1)
  const discount = calculateDiscount(subtotal);
  const total = subtotal - discount + shipping;

  const shippingLabel = shipping === 0 ? "FREE" : `$${shipping}`;
  const discountRow = discount > 0
    ? `<div class="cart-summary-row"><span>Discount</span><span class="discount-val">−$${discount}</span></div>`
    : "";

  container.innerHTML += `
    <div class="cart-summary-block">
      <div class="cart-summary-row"><span>Subtotal</span><span>$${subtotal}</span></div>
      <div class="cart-summary-row"><span>Shipping</span><span>${shippingLabel}</span></div>
      ${discountRow}
      <div class="cart-summary-row cart-total-final"><span>Total</span><span>$${total}</span></div>
    </div>
  `;
}

// ===================== REMOVE FROM CART =====================
// DSA ALGORITHM: Filtering O(n) — creates new array excluding removed item
window.removeFromCart = function (id) {
  cart = cart.filter(item => item.id !== id);

  updateCartCount();

  pushHistory(`Removed item from cart`);
  renderCart();
};

// ===================== CHANGE CART QUANTITY =====================
// DSA ALGORITHM: Linear search - O(n)
window.changeCartQuantity = function (id, delta) {
  const item = cart.find(cartItem => cartItem.id === id);
  if (!item) return;

  item.quantity += delta;
  if (item.quantity <= 0) {
    window.removeFromCart(id);
    return;
  }

  updateCartCount();
  pushHistory(delta > 0 ? "Increased quantity" : "Decreased quantity");
  renderCart();
};

// ===================== ADD TO CART =====================
// DSA ALGORITHM: Stack push — O(1)
window.addToCart = function (id) {
  const existingItem = cart.find(item => item.id === id);

  if (existingItem) {
    existingItem.quantity += 1;
  } else {
    cart.push({ id, quantity: 1 });
  }

  updateCartCount();

  pushHistory(existingItem ? "Increased quantity" : "Added to cart");
  renderCart();
  window.showToast();
};

// ===================== CHECKOUT =====================
window.goCheckout = function () {
  if (cart.length === 0) return;

  // Build checkout data for localStorage
  let subtotal = 0;
  const items = cart.map(item => {
    const p = productMap.get(item.id);
    const price = getProductPrice(p);
    const lineTotal = price * item.quantity;
    subtotal += lineTotal;
    return {
      id: p.id,
      name: p.name,
      image: p.image,
      price,
      quantity: item.quantity,
      lineTotal,
      category: p.category
    };
  });

  const shipping = calculateShipping(subtotal);
  const discount = calculateDiscount(subtotal);
  const total = subtotal - discount + shipping;

  // DSA ALGORITHM: Store checkout data in localStorage — O(n)
  localStorage.setItem("checkoutData", JSON.stringify({ items, subtotal, shipping, discount, total }));

  window.location.href = "checkout.html";
};

// ===================== RENDER PRODUCTS =====================
// DSA ALGORITHM: Filtering — O(n)
function renderProducts(list = products) {
  const container = document.getElementById("products");
  container.innerHTML = "";

  const filtered = list.filter(p => {
    const hasSaleFilter   = activeFilters.has('sale');
    const hasWomenFilter  = activeFilters.has('women');
    const hasMenFilter    = activeFilters.has('men');
    const price = getProductPrice(p);

    if (hasSaleFilter && !p.sale) return false;
    if (activeNameKeyword && !p.name.toLowerCase().includes(activeNameKeyword)) return false;
    if (price < activePriceRange.min || price > activePriceRange.max) return false;

    if (hasWomenFilter && hasMenFilter) {
      if (p.gender !== 'unisex') return false;
    } else if (hasWomenFilter) {
      if (!(p.gender === 'women' || p.gender === 'unisex')) return false;
    } else if (hasMenFilter) {
      if (!(p.gender === 'men' || p.gender === 'unisex')) return false;
    }

    if (currentCategory !== "all" && p.category !== currentCategory) return false;
    return true;
  });

  if (filtered.length === 0) {
    container.innerHTML = "<p class='products-empty'>No products found in this range.</p>";
    return;
  }

  filtered.forEach(p => {
    const isSale = p.sale;
    const finalPrice = getProductPrice(p);

    container.innerHTML += `
      <div class="card">
        <div class="card-img">
          ${isSale ? `<div class="card-badge sale-badge">-${p.salePercent}%</div>` : ""}
          <div class="card-img-placeholder">
            <img src="${p.image}" alt="${p.name}">
          </div>
          <div class="gender-tag ${p.gender === "women" ? "gender-nu" : "gender-nam"}">
            ${p.gender.toUpperCase()}
          </div>
        </div>
        <div class="card-body">
          <div class="card-category">${p.category.toUpperCase()}</div>
          <h3>${p.name}</h3>
          <div class="card-price-row">
            <span class="card-price ${isSale ? "sale-price" : ""}">$${finalPrice}</span>
            ${isSale ? `<span class="card-price-old">$${p.price}</span>` : ""}
          </div>
          <button class="btn-add" onclick="addToCart(${p.id})">ADD TO CART</button>
        </div>
      </div>
    `;
  });
}

// ===================== FILTER BY GENDER =====================
// DSA ALGORITHM: Filtering — O(n)
window.filterGender = function (g) {
  if (activeFilters.has(g)) {
    activeFilters.delete(g);
  } else {
    activeFilters.add(g);
  }

  document.querySelectorAll('.gender-pill').forEach(btn => {
    btn.classList.toggle('active', activeFilters.has(btn.dataset.filter));
  });

  if (g !== 'sale') {
    pushHistory(`Filtered: ${g.charAt(0).toUpperCase() + g.slice(1)}`);
  } else {
    pushHistory("Filtered: Sale items");
  }

  renderProducts();
};

// ===================== FILTER BY CATEGORY =====================
// DSA ALGORITHM: Filtering — O(n)
window.filterCategory = function (cat) {
  currentCategory = cat;
  document.querySelectorAll(".cat-pill").forEach(btn => {
    btn.classList.remove("active");
    if (btn.dataset.cat === cat) btn.classList.add("active");
  });

  pushHistory(`Filtered: ${cat.charAt(0).toUpperCase() + cat.slice(1)}`);
  renderProducts();
};

// ===================== RESET FILTER =====================
window.__resetFilters = function () {
  activeFilters.clear();
  currentCategory = "all";
  activeNameKeyword = "";
  activePriceRange = { ...defaultPriceRange };

  const searchNameInput = document.getElementById("search-name");
  const minInput = document.getElementById("price-min");
  const maxInput = document.getElementById("price-max");

  if (searchNameInput) searchNameInput.value = "";
  if (minInput) minInput.value = activePriceRange.min;
  if (maxInput) maxInput.value = activePriceRange.max;

  updatePriceRangeLabel();
  pushHistory("Reset filters");
  renderProducts();
};

// ===================== SEARCH BY NAME =====================
// DSA ALGORITHM: Linear Search - O(n)
window.searchName = function () {
  activeNameKeyword = document.getElementById("search-name").value.trim().toLowerCase();

  if (activeNameKeyword) {
    pushHistory(`Searched name: ${activeNameKeyword}`);
  }

  renderProducts();
};

// ===================== SORT =====================
// DSA ALGORITHM: Sorting — O(n log n)
window.sortProducts = function (order) {
  const sorted = [...products].sort((a, b) =>
    order === "asc" ? a.price - b.price : b.price - a.price
  );
  pushHistory(`Sorted: ${order === "asc" ? "Low to High" : "High to Low"}`);
  renderProducts(sorted);
};

// ===================== INIT =====================
initData();
renderHeroSaleSlider();
setupPriceRangeFilter();
renderProducts();
pushHistory("Visited shop");
