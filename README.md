🛒 Ecommerce Multivendor - Entity Yapısı
Bu yapı, çok satıcılı bir e-ticaret platformunun temel veri modelini tanımlar.

👤 User (Müşteri)
fullname: Ad Soyad

mail: E-posta

password: Şifre

mobile: Telefon numarası

user_role: Kullanıcı rolü

address: Kullanıcının adresleri (OneToMany)

usedCoupons: Kullanıcının kullandığı kuponlar (ManyToMany)

🧑‍💼 Seller (Satıcı)
sellername: Satıcı adı

mobile: Telefon numarası

mail: E-posta

password: Şifre

businessDetails: İş bilgileri

bankDetails: Banka bilgileri

address: Satıcı adresi (OneToOne)

GSTIN: Vergi kimlik numarası

role: Rol bilgisi

accountStatus: Hesap durumu (örneğin: aktif, askıda vb.)

🏠 Address (Adres)
name: Alıcı adı

plocality: Mahalle / bölge

address: Adres detayı

city: Şehir

state: İl

pinCode: Posta kodu

mobile: Telefon numarası

📦 Product (Ürün)
title: Ürün başlığı

description: Açıklama

mrpPrice: Etiket fiyatı

sellingPrice: Satış fiyatı

discountPercentage: İndirim oranı

quantity: Stok adedi

color: Renk

images: Ürün görselleri (Liste)

numRatings: Değerlendirme sayısı

createdAt: Oluşturulma tarihi

sizes: Mevcut bedenler

category: Kategori (ManyToOne)

seller: Satıcı (ManyToOne)

reviews: Yorumlar (OneToMany, Liste)

🗂️ Category (Kategori)
name: Kategori adı

categoryId: Benzersiz kategori kimliği

parentCategory: Üst kategori (ManyToOne)

level: Seviye (Ana kategori veya Alt kategori)

🎟️ Coupon (Kupon)
code: Kupon kodu

minimumOrderValue: Minimum sipariş tutarı

discountPercentage: İndirim oranı

validityStartDate: Başlangıç tarihi

validityEndDate: Bitiş tarihi

isActive: Kupon aktif mi? (Varsayılan: true)

usedByUsers: Kuponu kullanan kullanıcılar (ManyToMany, mappedBy = "usedCoupons")

📦 Order (Sipariş)
orderId: Sipariş numarası

user: Siparişi veren kullanıcı (ManyToOne)

sellerId: Satıcının kimliği

orderItems: Siparişe ait ürünler (OneToMany)

shippingAddress: Kargo adresi (ManyToOne)

paymentDetails: Ödeme bilgileri

totalMrpPrice: Toplam etiket fiyatı

discount: Uygulanan indirim

orderStatus: Sipariş durumu

totalItem: Toplam ürün adedi

paymentStatus: Ödeme durumu

orderDate: Sipariş tarihi

deliverDate: Teslim tarihi

💳 Transaction (İşlem)
customer: İşlemi yapan kullanıcı (ManyToOne)

order: İlgili sipariş (OneToOne)

seller: Ürünü sağlayan satıcı (ManyToOne)

❤️ Wish List (Favoriler)
user: Favori listesi sahibi (OneToOne)

products: Favorilere eklenen ürünler (ManyToMany)
