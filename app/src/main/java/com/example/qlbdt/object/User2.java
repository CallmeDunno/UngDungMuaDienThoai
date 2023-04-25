package com.example.qlbdt.object;

public class User2 {
        private String image;
        public User2(){}
        public User2(String image, String name, String price) {
                this.image = image;
                this.name = name;
                this.price = price;

        }

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getPrice() {
                return price;
        }

        public void setPrice(String price) {
                this.price = price;
        }

        private String name;
        private String price;


}
