import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { CartService } from 'app/cart/service/cart.service';
import { Product } from 'app/products/data-access/product.model';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-cart-dialog',
  standalone: true,
  imports: [DialogModule, DynamicDialogModule, ButtonModule, TableModule, CommonModule],
  templateUrl: './cart-dialog.component.html',
  styleUrl: './cart-dialog.component.css'
})
export class CartDialogComponent {
  cart: Product[] = [];
  visible = true;

  constructor(private cartService: CartService) { }

  ngOnInit() {
    this.cartService.cart$.subscribe(items => {
      this.cart = items;
    });
  }

  increaseQuantity(id: number) {
    this.cartService.updateQuantityinTheCart(id, 1);
  }

  decreaseQuantity(id: number) {
    this.cartService.updateQuantityinTheCart(id, -1);
  }

  removeFromCart(product: Product) {
    this.cartService.removeFromCart(product);
  }

  clearCart() {
    this.cartService.clearCart();
  }

  close() {
    this.visible = false;
  }


}
