import {
  Component, OnInit,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { DialogService, DynamicDialogModule, DynamicDialogRef } from "primeng/dynamicdialog";
import { CartDialogComponent } from "./cart/components/cart-dialog/cart-dialog.component";
import { ButtonModule } from "primeng/button";
import { CartService } from "./cart/service/cart.service";
import { BadgeModule } from 'primeng/badge';
import { CommonModule } from "@angular/common";
import { DialogModule } from "primeng/dialog";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [CommonModule, RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, DynamicDialogModule, ButtonModule, BadgeModule, CartDialogComponent, DialogModule],
  providers: [DialogService]
})
export class AppComponent implements OnInit {
  title = "ALTEN SHOP";

  ref: DynamicDialogRef | null = null;

  cartItemCount = 0;

  isDialogVisible = false;

  constructor(private dialogService: DialogService, private cartService: CartService) { }

  ngOnInit(): void {
    this.cartService.cart$.subscribe(items => {
      this.cartItemCount = items.reduce((sum, product) => sum + product.quantity, 0);
    });
  }

  showCartDialog() {
    this.isDialogVisible = true;
  }

}
