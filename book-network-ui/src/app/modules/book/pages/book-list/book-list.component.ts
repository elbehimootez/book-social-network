import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {BookService} from "../../../../services/services";
import {PageResponseBookResponse} from "../../../../services/models/page-response-book-response";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit{
  page : number=0;
  size : number = 5;
  bookResponse : PageResponseBookResponse = {};

 constructor(
     private bookService: BookService,
     private router:Router){
 }

  ngOnInit(): void {
   this.findAllBooks();
  }

  private findAllBooks() {
    this.bookService.findAllBooks(
      {
        page: this.page,
        size: this.size
      }).subscribe({
      next:(books)=>{
         this.bookResponse = books;
      }
    });
  }
}
