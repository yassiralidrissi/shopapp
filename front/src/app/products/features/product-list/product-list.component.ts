import { CommonModule } from "@angular/common";
import { Component, OnInit, inject, signal } from "@angular/core";
import { CartService } from "app/cart/service/cart.service";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ConfirmationService, MessageService } from "primeng/api";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { InputTextModule } from "primeng/inputtext";
import { FormsModule } from "@angular/forms";

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [CommonModule, DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, ToastModule, ConfirmPopupModule, InputTextModule, FormsModule ],
  providers: []
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);

  public readonly products = this.productsService.products;

  productItems: Product[] = [];
  paginatedProducts: any[] = []; 
  filteredProducts: any[] = [];

  searchQuery: string = '';

  currentPage = 1;
  productsPerPage = 5;
  totalPages = 0;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  constructor(private cartService: CartService, private messageService: MessageService, private confirmationService: ConfirmationService) { }

  ngOnInit() {
    this.productsService.get().subscribe(response => {
      this.productItems = response;
      this.filteredProducts = response;
      this.updatePagination();
    });
  }

  updatePagination() {
    this.totalPages = Math.ceil(this.filteredProducts.length / this.productsPerPage);
    const startIndex = (this.currentPage - 1) * this.productsPerPage;
    this.paginatedProducts = this.filteredProducts.slice(startIndex, startIndex + this.productsPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePagination();
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePagination();
    }
  }

  searchProducts() {
    this.filteredProducts = this.productItems.filter((product) =>
      product.name.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
    this.currentPage = 1;
    this.updatePagination();
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  confirmDeletion(event: Event, product: Product) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: 'Voulez-vous supprimer cet enregistrement ?',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass: 'p-button-danger p-button-sm',
      accept: () => {
        this.onDelete(product);
        this.messageService.add({ severity: 'success', summary: 'Confirmation', detail: `Produit ${product.name} est Supprimé`, key: 'br', life: 3000 });
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Annulation', detail: 'Suppression Annulée', key: 'br', life: 3000 });
      }
    });
  }

  public onAddToCart(product: Product) {
    this.cartService.addToCart(product);
    this.messageService.add({ severity: 'success', summary: 'Panier', detail: `Produit ${product.name} est ajouté au panier`, key: 'br', life: 3000 });
  }

  public getInventoryLabel(status: string) {
    switch (status) {
      case 'INSTOCK': return 'In Stock';
      case 'LOWSTOCK': return 'Low Stock';
      case 'OUTOFSTOCK': return 'Out of Stock';
      default: return 'Unknown';
    }
  }

  public getStarArray(count: number) {
    return Array(Math.max(0, count)).fill(0);
  }


}
