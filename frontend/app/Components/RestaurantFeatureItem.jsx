import Image from "next/image";

const restaurantPool = [
    {
        name: "The Burger Palace",
        cuisine: "Burgers • American",
        rating: 4.9,
        duration: "12-15 min",
        distance: "1.3km",
        price: "$$",
        image: "/boursin_burgers.jpg",
        items: [5, 2, 7] // MenuItems ids, for backend logging
    },
    {
        name: "Pizza Paradise",
        cuisine: "Pizza • Italian",
        rating: 4.7,
        duration: "15-20 min",
        distance: "2.1km",
        price: "$",
        image: null,
        items: [1, 2, 6]
    },
    {
        name: "Sushi Sushi",
        cuisine: "Sushi • Japanese",
        rating: 4.8,
        duration: "20-25 min",
        distance: "1.8km",
        price: "$$$",
        image: null,
        items: [3, 4, 7]
    },
    {
        name: "Grande Tacos",
        cuisine: "Tacos • Mexican",
        rating: 4.6,
        duration: "10-15 min",
        distance: "0.9km",
        price: "$",
        image: null,
        items: [4, 5, 6]
    },
    {
        name: "Green Bowl",
        cuisine: "Salads • Healthy",
        rating: 4.8,
        duration: "8-12 min",
        distance: "1.5km",
        price: "$$",
        image: null,
        items: [3, 6, 7]
    },
    {
        name: "Wing Go",
        cuisine: "Wings • American",
        rating: 4.5,
        duration: "15-18 min",
        distance: "2.3km",
        price: "$$",
        image: null,
        items: [4, 7, 2]
    },
];

export { restaurantPool };

export default function RestaurantFeatureItem({ restaurant, onQuickOrder }) {
    return (
        <div className="flex border-2 border-black/20 rounded-2xl overflow-hidden bg-white hover:scale-102 hover:shadow-lg duration-300">
            <div className="w-2/5 bg-gray-200">
                {restaurant.image ? (
                    <Image
                        className="object-cover h-full w-full"
                        src={restaurant.image}
                        alt={restaurant.name}
                        width={200}
                        height={200}
                    />
                ) : (
                    <div className="h-full w-full flex items-center justify-center text-6xl">
                        someone cook up an image
                    </div>
                )}
            </div>

            <div className="flex flex-col p-5 w-3/5">
                <div className="flex flex-col justify-between h-full">
                    {/* CONTAINER: Heading + RDD Container */}
                    <div>
                        {/* CONTAINER: Heading with Add Button */}
                        <div className="flex justify-between items-start mb-2">
                            <div>
                                <h2 className="text-2xl">{restaurant.name}</h2>
                                <h3 className="text-xl opacity-70">{restaurant.cuisine}</h3>
                            </div>
                            <button
                                onClick={() => onQuickOrder(restaurant)}
                                className="bg-purple-700 text-white px-4 py-2 rounded-lg hover:bg-purple-800 text-sm font-semibold whitespace-nowrap"
                            >
                                + Add
                            </button>
                        </div>

                        <div className="flex justify-between pt-5">
                            {/* Rating */}
                            <div className="flex items-center gap-1">
                                <Image
                                    src="/star.svg"
                                    alt="star icon"
                                    width={19}
                                    height={19}
                                />
                                <p className="text-xl opacity-70">{restaurant.rating}</p>
                            </div>

                            {/* Duration */}
                            <div className="flex items-center gap-1">
                                <Image
                                    src="/clock.svg"
                                    alt="clock icon"
                                    width={19}
                                    height={19}
                                />
                                <p className="text-xl opacity-70">{restaurant.duration}</p>
                            </div>

                            {/* Distance */}
                            <div className="flex items-center gap-1">
                                <Image
                                    src="/location.svg"
                                    alt="location icon"
                                    width={19}
                                    height={19}
                                />
                                <p className="text-xl opacity-70">{restaurant.distance}</p>
                            </div>
                        </div>
                    </div>

                    <div className="flex justify-between items-center">
                        <p className="text-xl opacity-70">{restaurant.price}</p>
                        <div className="p-1 px-2 rounded-3xl bg-[#34C75966]">
                            <p className="text-base text-[#1B9F3C]">
                                Free Robot Delivery
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}