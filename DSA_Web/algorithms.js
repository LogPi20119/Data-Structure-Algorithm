import { products } from "./data.js";

// ===================== DSA ALGORITHM: Stack — O(1) push =====================
// History tracked via Stack data structure (LIFO)
export const historyStack = [];

export function pushHistory(action) {
  // DSA ALGORITHM: Stack History — push O(1)
  historyStack.push(action);

  const historyList = document.getElementById("history-list");
  if (historyList) {
    historyList.innerHTML = "";
    // Render top 5 most recent (reverse order — newest first)
    [...historyStack].reverse().slice(0, 5).forEach(item => {
      const div = document.createElement("div");
      div.className = "history-item";
      div.textContent = item;
      historyList.appendChild(div);
    });
  }
}

// ===================== DSA ALGORITHM: Sort — O(n log n) =====================
export function sortByPrice() {
  // Time Complexity: O(n log n) — Array.sort uses TimSort
  return [...products].sort((a, b) => a.price - b.price);
}

// ===================== DSA ALGORITHM: Binary Search — O(log n) =====================
export function binarySearch(price) {
  // Must sort first: O(n log n), then search: O(log n)
  const arr = sortByPrice();
  let left = 0, right = arr.length - 1;

  while (left <= right) {
    const mid = Math.floor((left + right) / 2);
    if (arr[mid].price === price) return arr[mid];
    if (arr[mid].price < price) left = mid + 1;
    else right = mid - 1;
  }
  return null;
}

// ===================== DSA ALGORITHM: Recursion — O(n) =====================
export function totalRecursive(cart, index = 0) {
  // Time Complexity: O(n), Space: O(n) call stack
  if (index >= cart.length) return 0;
  return cart[index].quantity + totalRecursive(cart, index + 1);
}

// ===================== DSA ALGORITHM: Linked List — add O(1), toArray O(n) =====================
class Node {
  constructor(data) {
    this.data = data;
    this.next = null;
  }
}

export class LinkedList {
  constructor() { this.head = null; }

  add(data) {
    // O(1) prepend
    const newNode = new Node(data);
    newNode.next = this.head;
    this.head = newNode;
  }

  toArray() {
    // O(n) traversal
    let arr = [], cur = this.head;
    while (cur) { arr.push(cur.data); cur = cur.next; }
    return arr;
  }
}
