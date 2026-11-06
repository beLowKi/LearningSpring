-- Seed data for the `monmart` database (roughly 10 rows per table).
--
-- Run with:
--   mysql -u root -p monmart < src/main/resources/db/queries/seed_test_data.sql
--
-- All IDs are fixed UUIDs, so the script can be executed repeatedly without
-- creating duplicates: existing rows are updated in place.

START TRANSACTION;

-- ---------------------------------------------------------------------------
-- Categories (10)
-- ---------------------------------------------------------------------------
INSERT INTO categories (id, name)
VALUES (UUID_TO_BIN('c0000000-0000-4000-8000-000000000001'), 'Electronics'),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000002'), 'Books'),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000003'), 'Home & Kitchen'),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000004'), 'Clothing'),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000005'), 'Sports & Outdoors'),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000006'), 'Toys & Games'),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000007'), 'Beauty'),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000008'), 'Grocery'),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000009'), 'Office Supplies'),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000010'), 'Automotive')
AS new
ON DUPLICATE KEY UPDATE name = new.name;

-- ---------------------------------------------------------------------------
-- Products (10)
-- ---------------------------------------------------------------------------
INSERT INTO products (id, name, about, price)
VALUES (UUID_TO_BIN('a0000000-0000-4000-8000-000000000001'), 'Wireless Headphones',
        'Over-ear Bluetooth headphones with active noise cancellation and 30-hour battery life.', 79.99),
       (UUID_TO_BIN('a0000000-0000-4000-8000-000000000002'), 'Mechanical Keyboard',
        'Hot-swappable mechanical keyboard with RGB backlighting and a USB-C connection.', 129.50),
       (UUID_TO_BIN('a0000000-0000-4000-8000-000000000003'), 'Sci-Fi Novel',
        'Award-winning science fiction novel about a generation ship lost between the stars.', 14.99),
       (UUID_TO_BIN('a0000000-0000-4000-8000-000000000004'), 'Weeknight Cookbook',
        'Collection of 100 quick weeknight recipes with full-color photographs.', 24.00),
       (UUID_TO_BIN('a0000000-0000-4000-8000-000000000005'), 'Ceramic Mug',
        'Hand-glazed 350 ml ceramic mug. Dishwasher and microwave safe.', 12.50),
       (UUID_TO_BIN('a0000000-0000-4000-8000-000000000006'), 'Cotton T-Shirt',
        'Unisex organic cotton t-shirt in a relaxed fit.', 19.99),
       (UUID_TO_BIN('a0000000-0000-4000-8000-000000000007'), 'Yoga Mat',
        'Non-slip 6 mm yoga mat with a carrying strap.', 29.95),
       (UUID_TO_BIN('a0000000-0000-4000-8000-000000000008'), 'Building Blocks Set',
        '250-piece building blocks set for ages 6 and up.', 44.00),
       (UUID_TO_BIN('a0000000-0000-4000-8000-000000000009'), 'Facial Cleanser',
        'Gentle daily facial cleanser for sensitive skin, 200 ml.', 15.75),
       (UUID_TO_BIN('a0000000-0000-4000-8000-000000000010'), 'Bamboo Desk Organizer',
        'Bamboo desk organizer with five compartments for pens and paper.', 22.49)
AS new
ON DUPLICATE KEY UPDATE name  = new.name,
                        about = new.about,
                        price = new.price;

-- ---------------------------------------------------------------------------
-- Product <-> Category links (12)
-- ---------------------------------------------------------------------------
INSERT IGNORE INTO product_categories (category_id, product_id)
VALUES (UUID_TO_BIN('c0000000-0000-4000-8000-000000000001'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000001')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000001'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000002')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000009'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000002')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000002'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000003')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000002'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000004')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000003'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000004')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000003'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000005')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000004'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000006')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000005'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000007')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000006'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000008')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000007'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000009')),
       (UUID_TO_BIN('c0000000-0000-4000-8000-000000000009'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000010'));

-- ---------------------------------------------------------------------------
-- Customers (10)
-- ---------------------------------------------------------------------------
INSERT INTO customers (id, name, email, password, address)
VALUES (UUID_TO_BIN('d0000000-0000-4000-8000-000000000001'), 'Alice Johnson', 'alice.johnson@example.com',
        'test-password-1', '12 Maple Street, Springfield, IL 62701'),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000002'), 'Brian Lee', 'brian.lee@example.com',
        'test-password-2', '48 Oak Avenue, Portland, OR 97201'),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000003'), 'Carmen Ruiz', 'carmen.ruiz@example.com',
        'test-password-3', '901 Sunset Boulevard, Los Angeles, CA 90012'),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000004'), 'David Chen', 'david.chen@example.com',
        'test-password-4', '7 Pine Court, Seattle, WA 98101'),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000005'), 'Emma Wilson', 'emma.wilson@example.com',
        'test-password-5', '23 Birch Lane, Austin, TX 73301'),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000006'), 'Farid Haddad', 'farid.haddad@example.com',
        'test-password-6', '310 Elm Road, Boston, MA 02108'),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000007'), 'Grace Kim', 'grace.kim@example.com',
        'test-password-7', '65 Willow Drive, Denver, CO 80202'),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000008'), 'Henry Patel', 'henry.patel@example.com',
        'test-password-8', '184 Cedar Way, Chicago, IL 60601'),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000009'), 'Isabel Novak', 'isabel.novak@example.com',
        'test-password-9', '52 Aspen Circle, Nashville, TN 37201'),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000010'), 'Jamal Brooks', 'jamal.brooks@example.com',
        'test-password-10', '9 Spruce Street, Miami, FL 33101')
AS new
ON DUPLICATE KEY UPDATE name     = new.name,
                        email    = new.email,
                        password = new.password,
                        address  = new.address;

-- ---------------------------------------------------------------------------
-- Orders (10) - one per customer
-- ---------------------------------------------------------------------------
INSERT INTO orders (id, customer_id, shipping_address_snapshot, created_at)
VALUES (UUID_TO_BIN('e0000000-0000-4000-8000-000000000001'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000001'),
        '12 Maple Street, Springfield, IL 62701', '2026-09-02 10:15:00'),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000002'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000002'),
        '48 Oak Avenue, Portland, OR 97201', '2026-09-08 14:42:00'),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000003'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000003'),
        '901 Sunset Boulevard, Los Angeles, CA 90012', '2026-09-15 09:05:00'),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000004'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000004'),
        '7 Pine Court, Seattle, WA 98101', '2026-09-21 18:30:00'),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000005'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000005'),
        '23 Birch Lane, Austin, TX 73301', '2026-09-27 12:00:00'),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000006'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000006'),
        '310 Elm Road, Boston, MA 02108', '2026-10-03 08:20:00'),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000007'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000007'),
        '65 Willow Drive, Denver, CO 80202', '2026-10-09 16:55:00'),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000008'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000008'),
        '184 Cedar Way, Chicago, IL 60601', '2026-10-15 11:10:00'),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000009'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000009'),
        '52 Aspen Circle, Nashville, TN 37201', '2026-10-21 19:45:00'),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000010'), UUID_TO_BIN('d0000000-0000-4000-8000-000000000010'),
        '9 Spruce Street, Miami, FL 33101', '2026-10-26 13:25:00')
AS new
ON DUPLICATE KEY UPDATE customer_id               = new.customer_id,
                        shipping_address_snapshot = new.shipping_address_snapshot,
                        created_at                = new.created_at;

-- ---------------------------------------------------------------------------
-- Order items (13) - a few orders contain more than one product
-- ---------------------------------------------------------------------------
INSERT INTO order_items (order_id, product_id, quantity, unit_price)
VALUES (UUID_TO_BIN('e0000000-0000-4000-8000-000000000001'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000001'), 1, 79.99),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000001'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000005'), 2, 12.50),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000002'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000002'), 1, 129.50),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000003'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000003'), 3, 14.99),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000003'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000004'), 1, 24.00),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000004'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000006'), 2, 19.99),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000005'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000007'), 1, 29.95),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000006'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000008'), 1, 44.00),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000007'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000009'), 1, 15.75),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000007'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000005'), 1, 12.50),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000008'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000010'), 1, 22.49),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000009'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000001'), 1, 79.99),
       (UUID_TO_BIN('e0000000-0000-4000-8000-000000000010'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000004'), 1, 24.00)
AS new
ON DUPLICATE KEY UPDATE quantity   = new.quantity,
                        unit_price = new.unit_price;

-- ---------------------------------------------------------------------------
-- Cart items (10) - one item per customer
-- ---------------------------------------------------------------------------
INSERT INTO cart_items (customer_id, product_id, quantity)
VALUES (UUID_TO_BIN('d0000000-0000-4000-8000-000000000001'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000002'), 1),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000002'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000004'), 1),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000003'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000006'), 1),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000004'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000008'), 2),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000005'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000010'), 1),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000006'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000001'), 1),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000007'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000003'), 2),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000008'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000005'), 4),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000009'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000007'), 1),
       (UUID_TO_BIN('d0000000-0000-4000-8000-000000000010'), UUID_TO_BIN('a0000000-0000-4000-8000-000000000009'), 1)
AS new
ON DUPLICATE KEY UPDATE quantity = new.quantity;

COMMIT;
