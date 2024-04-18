lab-linked-lists
================

Used code from a repository from Sam Rebelsky that was the baselinen of Lists, Nodes and the SimpleDLLs. 
Got help getting started with the fail safe iterators from Alyssa Trapp

The purpose of this was to implement 2 things to basic linked lists, a dummy node which would allow circular linked-ness and fail safe iterators.
Dummy nodes simplify the code because we only need to deal with special cases at the creation of a list, instead of checking for special cases in every single method. If there was no dummy list, we couldn't have it be circular which means the iterator can get stuck in weird places, like before the first item or after the last. With the dummy node effectivly before the first and after the last, there is no worry of iterator.next or iterator.prev not existing. We were able to keep most of the methods the same, because hasNext and hasPrev went off of the size and not the actual linkage. Now we can get rid of all the if else checks with special cases to adding and subtracting near the ends of the list. All I had to do was create a method to check the counter of the list the iterator which would throw an exception if they didn't match, and call the method before every other method.

Github:  https://github.com/willettl/mp8-linked-lists 
