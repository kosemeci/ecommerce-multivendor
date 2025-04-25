Ecommerce Multivendor

**Entity Management**

-**User**
  *fullname
  *mail
  *password
  *mobile
  *user_role
  *address(OneToMany)
  *usedCoupons(ManyToMany)
  
-**Seller**
  -sellername
  -mobile
  -mail
  -password
  -businessDetails
  -bankDetails
  -address(OneToOne)
  -GSTIN(Tax Identification Number)
  -role
  -accountStatus
  
-**Address**
  *name
  *name
  *plocality
  *address
  *city
  *state
  *pinCode
  *mobile
  
-**Product**
  *title
  *description
  *mrpPrice
  *sellingPrice
  *discountPercentage
  *quantity
  *color
  *images(List)
  *numRatings
  *createdAt
  *sizes
  *category(ManyToOne)
  *seller(ManyToOne)
  *reviews (List),(OneToMany)
  
-**Category**
  *name
  *categoryId
  *parentCategory(ManyToOne)
  *level(Main or Sub category)

-**Coupon**
  *code
  *minimumOrderValue
  *discountPercentage
  *validityStartDate
  *validityEndDate
  *isActive = true
    @ManyToMany(mappedBy = "usedCoupons")
  *Set<User> usedByUsers = new HashSet<>()

-**Order**
  *orderId
  *user(ManyToOne)
  *sellerId
  @OneToMany(mappedBy="order")
  *List<OrderItem> orderItems = new ArrayList<>()
  *shippingAddress(ManyToOne)
  *paymentDetails
  *totalMrpPrice
  *discount
  *orderStatus
  *totalItem
  *paymentStatus
  *orderDate
  *deliverDate

-**Transaction**
  *@ManyToOne
   User customer
  *@OneToOne
   Order order
  *@ManyToOne
   Seller seller

-**Wish List**
  @OneToOne
  User user;
  @ManyToMany
  Set<Product> products

  
