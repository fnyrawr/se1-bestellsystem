package system.impl;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import system.Repository;

class DataRepositoryImpl {

    private Repository<Customer> customerRepository;
    private Repository<Article> articleRepository;
    private Repository<Order> orderRepository;

    public DataRepositoryImpl() {
        customerRepository = new RepositoryImpl<Customer>();
        articleRepository = new RepositoryImpl<Article>();
        orderRepository = new RepositoryImpl<Order>();
    }

    public Repository<Customer> getCustomerRepository() {
        return customerRepository;
    };

    public Repository<Article> getArticleRepository() {
        return articleRepository;
    };

    public Repository<Order> getOrderRepository() {
        return orderRepository;
    };

}
